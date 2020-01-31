package com.smuniov.restaurantServiceSystem.service.impl;

import com.smuniov.restaurantServiceSystem.DTO.DishesInOrderDTO;
import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.Exception.BadRequestException;
import com.smuniov.restaurantServiceSystem.entity.food.Dish;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.repository.*;
import com.smuniov.restaurantServiceSystem.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderServiceI {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishesInOrderRepository dishesInOrderRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public List findAllOrders(Client client) {
        return orderRepository.getAllByClientOrderByTimestampAsc(client);
    }

    @Override
    public List getAllByClient_IdOrderByTimestamp(int id) {
       return orderRepository.getAllByClient_IdOrderByTimestamp(id);
    }

    @Override
    public List<DishesInOrder> findAllDishesInOrderByOrder_Id(int orderId) {
        return dishesInOrderRepository.findAllDishesInOrderByOrder_Id(orderId);
    }

    @Override
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public List<Table> getAllTablesByReserved(boolean isReserved) {
        return tableRepository.findAllByIsReserved(isReserved);
    }

    @Override
    public void changeTableStatus(Table table) {
        tableRepository.changeTableStatus(table.getIsReserved(), table.getId());
    }

    @Override
    public Table findTableById(int id) {
        return tableRepository.findById(id);
    }

    @Override
    public boolean addDishesInOrder(List<DishesInOrderDTO> dishesInOrderDTOList) {
        return false;
    }

    @Override
    public boolean orderInit(Client client, List<DishesInOrderDTO> dishesInOrderDTOList) {
        double balance = checkDeposit(client, dishesInOrderDTOList);
        if(balance < 0.){
            throw new BadRequestException("client need " + -balance + " to make an order");
        }
        Order order = new Order();
        order.setClient(client);
        order.setOrderStatus("NEW");
        order.setTimestamp(System.currentTimeMillis());
        List<DishesInOrder> dishesInOrderList = new ArrayList<>();
        for (int i = 0; i < dishesInOrderDTOList.size(); i++) {
            DishesInOrder tempDishInOrder = new DishesInOrder();
            tempDishInOrder.setOrder(order);
            tempDishInOrder.setDish(dishRepository.
                    getOne(dishesInOrderDTOList.get(i).getDishId()));
            tempDishInOrder.setQuantity(dishesInOrderDTOList.get(i).getQuantity());
            dishesInOrderList.add(tempDishInOrder);
        }
        dishesInOrderRepository.saveAll(dishesInOrderList);
        order.setDishes(dishesInOrderList);
        orderRepository.save(order);

        return true;
    }

    @Override
    public double checkDeposit(Client client, List<DishesInOrderDTO> dishesInOrderDTOList) {
        double invoice = 0;
        for (int i = 0; i < dishesInOrderDTOList.size(); i++) {
            invoice += dishesInOrderDTOList.get(i).getPrice()*dishesInOrderDTOList.get(i).getQuantity();
        }
        return client.getDeposit()-invoice;
    }

    @Override
    public void bookTable(int tableId, Order order) {
        if(order.getOrderStatus() !=null && order.getTable() != null && order.getOrderStatus().equals("NEW")) {
            Table tableToBook = tableRepository.getOne(tableId);
            if (tableToBook.getIsReserved()) {
                throw new BadRequestException("table is booked");
            }
            tableToBook.setReserved(true);
            tableRepository.save(tableToBook);
            order.setTable(tableToBook);
            tableRepository.save(tableToBook);
        } throw new BadRequestException("you cannot book table if you have done it already, or dont init order");
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.getOne(id);
    }

    @Override
    public void processOrder(int orderId, Employee waiter, Employee cook) {
        Order orderToProcess = orderRepository.getOne(orderId);
        orderToProcess.setOrderStatus("IS_PROCESSING");
        if(waiter.getLoadFactor() == 5){
            orderToProcess.setOrderStatus("WAITER_QUEUE");
            orderRepository.save(orderToProcess);
            throw new BadRequestException(" no free waiters!!!");
        }
        if(cook.getLoadFactor() == 5){//
            orderToProcess.setOrderStatus("COOK_QUEUE");
            orderRepository.save(orderToProcess);
            throw new BadRequestException(" no free cooks!!!");
        }
        if(cook.getLoadFactor() == 5 && waiter.getLoadFactor() == 5){
            orderToProcess.setOrderStatus("ALL_QUEUE");
            throw new BadRequestException(" no all kinds of employee!!!");
        }

        orderToProcess.setWaiter(waiter);
        waiter.setLoadFactor(waiter.getLoadFactor() + 1);
        orderToProcess.setCook(cook);
        cook.setLoadFactor(cook.getLoadFactor() + 1);
        employeeRepository.save(waiter);
        employeeRepository.save(cook);
    }

    public void finishOrder(Order order){
//load, balance,status
        Employee waiter = order.getWaiter();
        waiter.setLoadFactor(waiter.getLoadFactor() - 1);
        employeeRepository.save(waiter);
        Employee cook = order.getCook();
        cook.setLoadFactor(cook.getLoadFactor() - 1);
        employeeRepository.save(cook);
        Client client = order.getClient();
        List<DishesInOrder> dishesInOrderList = order.getDishes();
        double invoice = 0.;
        for (int i = 0; i < dishesInOrderList.size(); i++) {
            invoice += dishesInOrderList.get(i).getDish().getPrice()*dishesInOrderList.get(i).getQuantity();
        }
        client.setDeposit(client.getDeposit() - invoice);
        clientRepository.save(client);
        order.setOrderStatus("IS_FINISHED");
        order.setInvoice(invoice);
        orderRepository.save(order);
        Table table = order.getTable();
        table.setReserved(false);
        tableRepository.save(table);
    }

    public void addSaveUpdateDish(Dish dish){
        dishRepository.save(dish);
    }

    public List<Dish> getAllDishes(){
        return dishRepository.findAll();
    }

    public void deleteDish(int dishId){
        dishRepository.deleteById(dishId);
    }

    public Optional getDish(int dishId){
        return dishRepository.findById(dishId);
    }


}
