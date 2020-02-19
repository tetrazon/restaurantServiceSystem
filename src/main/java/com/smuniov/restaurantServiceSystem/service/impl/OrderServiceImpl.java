package com.smuniov.restaurantServiceSystem.service.impl;

import com.smuniov.restaurantServiceSystem.DTO.DishesInOrderDTO;
import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.Exception.BadRequestException;
import com.smuniov.restaurantServiceSystem.controller.OrdersController;
import com.smuniov.restaurantServiceSystem.entity.food.Dish;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.repository.*;
import com.smuniov.restaurantServiceSystem.service.OrderServiceI;
import org.apache.logging.log4j.LogManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Transactional
public class OrderServiceImpl implements OrderServiceI {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final DishesInOrderRepository dishesInOrderRepository;
    private final TableRepository tableRepository;
    private final EmployeeRepository employeeRepository;
    private final DishRepository dishRepository;
    private final ClientRepository clientRepository;

    public OrderServiceImpl(OrderRepository orderRepository, DishesInOrderRepository dishesInOrderRepository,
                            TableRepository tableRepository, EmployeeRepository employeeRepository,
                            DishRepository dishRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.dishesInOrderRepository = dishesInOrderRepository;
        this.tableRepository = tableRepository;
        this.employeeRepository = employeeRepository;
        this.dishRepository = dishRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public List findAllOrders(Client client) {
        return orderRepository.getAllByClientOrderByTimestampAsc(client);
    }

    public Page<Order> findAllOrders(Pageable pageable){
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> findAllOrdersByClientId(Pageable pageable, int clientId) {
        return orderRepository.getAllByClient_Id(pageable, clientId);
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

    public void createTable(Table table){
        if(table == null){
            throw new BadRequestException("empty table!");
        }
        tableRepository.save(table);
    }

    public void updateTable(Table table){
        if (table == null || table.getId()== 0){
            throw new BadRequestException("you cannot update empty table");
        }
        createTable(table);
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

    public OrderDTO orderInit(Client client, OrderDTO orderDTO){
        if (orderDTO.getDishesInOrderDTOS().size() == 0){
            throw new BadRequestException("you cannot create empty order");
        }
        if (orderDTO.getTableId() == 0){
            throw new BadRequestException("you cannot make the order without table");
        }
        double balance = checkDeposit(client, orderDTO.getDishesInOrderDTOS());
        if(balance < 0.){
            throw new BadRequestException("client need " + -balance + " to make an order");
        }
        Order order = new Order();
        order.setClient(client);
        order.setOrderStatus("NEW");
        order.setTimestamp(System.currentTimeMillis());
        List<DishesInOrder> dishesInOrderList = new ArrayList<>();
        for (int i = 0; i < orderDTO.getDishesInOrderDTOS().size(); i++) {
            DishesInOrder tempDishInOrder = new DishesInOrder();
            tempDishInOrder.setOrder(order);
            tempDishInOrder.setDish(dishRepository.
                    getOne(orderDTO.getDishesInOrderDTOS().get(i).getDishId()));
            tempDishInOrder.setQuantity(orderDTO.getDishesInOrderDTOS().get(i).getQuantity());
            dishesInOrderList.add(tempDishInOrder);
        }
        dishesInOrderRepository.saveAll(dishesInOrderList);
        order.setDishes(dishesInOrderList);
        bookTable(orderDTO.getTableId());
        order.setTable(tableRepository.findById(orderDTO.getTableId()));
        orderRepository.save(order);
        OrderDTO orderDTOresp = new OrderDTO(order);
        orderDTOresp.add(linkTo(
                methodOn(OrdersController.class)
                        .processOrder(orderDTOresp.getId()))
                        .withRel("process"));
        logger.info("client with id: " + client.getId() + " has created new order with id: " + order.getId());
        return orderDTOresp;
    }

    @Override
    public double checkDeposit(Client client, List<DishesInOrderDTO> dishesInOrderDTOList) {
        double invoice = 0;
        for (int i = 0; i < dishesInOrderDTOList.size(); i++) {
            invoice += dishesInOrderDTOList.get(i).getPrice()*dishesInOrderDTOList.get(i).getQuantity();
        }
        return client.getDeposit()-invoice;
    }

    public void bookTable(int tableId){
        Table tableToBook = tableRepository.getOne(tableId);
        if (tableToBook.getIsReserved()) {
            throw new BadRequestException("this table is booked, choose another one");
        }
        tableToBook.setReserved(true);
        tableRepository.save(tableToBook);
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.getOne(id);
    }

    @Override
    public void processOrder(int orderId, Employee waiter, Employee cook) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isPresent()){
            Order orderToProcess = orderOptional.get();
            String orderStatus = orderToProcess.getOrderStatus();
            if (orderStatus.equals("IS_FINISHED")) {
                throw new BadRequestException("you cannot process finished order!");
            }
            if (orderStatus.equals("NEW")) {
                orderToProcess.setOrderStatus("IS_PROCESSING");
                if (cook.getLoadFactor() == 5 && waiter.getLoadFactor() == 5) {
                    orderToProcess.setOrderStatus("ALL_QUEUE");
                    throw new BadRequestException(" no all kinds of employee!!!");
                }
                if (waiter.getLoadFactor() == 5) {
                    orderToProcess.setOrderStatus("WAITER_QUEUE");
                    orderRepository.save(orderToProcess);
                    throw new BadRequestException(" no free waiters!!!");
                }
                if (cook.getLoadFactor() == 5) {
                    orderToProcess.setOrderStatus("COOK_QUEUE");
                    orderRepository.save(orderToProcess);
                    throw new BadRequestException(" no free cooks!!!");
                }

                orderToProcess.setWaiter(waiter);
                waiter.setLoadFactor(waiter.getLoadFactor() + 1);
                orderToProcess.setCook(cook);
                cook.setLoadFactor(cook.getLoadFactor() + 1);
                employeeRepository.save(waiter);
                employeeRepository.save(cook);
            } else {
                throw new BadRequestException(" no all kinds of employee, or some of them!!!");
            }
        } else {
            throw new BadRequestException(" no order present!!!");
        }
    }


    public void finishOrder(Order order){
        if(order.getOrderStatus() == null ){
            throw new BadRequestException("bad order!");
        }
        if(!order.getOrderStatus().equals("IS_PROCESSING")){
            throw new BadRequestException("you cannot finish order, which has no status IS_PROCESSING!");
        }
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
        logger.info("order with id " + order.getId() + " is finished");
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
