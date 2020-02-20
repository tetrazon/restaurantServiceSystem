package com.smuniov.restaurantServiceSystem.service;

import com.smuniov.restaurantServiceSystem.DTO.DishesInOrderDTO;
import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.entity.food.Dish;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderServiceI {
    List findAllOrders(Client client);
    Page<Order> findAllOrders(Pageable pageable);
    Page<Order> findAllOrdersByClientId(Pageable pageable, int clientId);
    List getAllByClient_IdOrderByTimestamp(int id);
    List<DishesInOrder> findAllDishesInOrderByOrder_Id(int orderId);
    List<Table> getAllTables();
    public void createTable(Table table);
    void updateTable(Table table);
    List<Table> getAllTablesByReserved(boolean isReserved);
    void changeTableStatus(Table table);
    Table findTableById(int id);
    boolean addDishesInOrder(List<DishesInOrderDTO> dishesInOrderDTOList);
    OrderDTO orderInit(Client client, OrderDTO orderDTO);
    double checkDeposit(Client client, List<DishesInOrderDTO> dishesInOrderDTOList);
    void bookTable(int tableId);
    Order getOrderById (int id);
    //void processOrder(int orderId, Employee waiter, Employee cook);
    void processOrder(int orderId);
    void finishOrder(Order order);
    void addSaveUpdateDish(Dish dish);
    List<Dish> getAllDishes();
    void deleteDish(int dishId);
    Optional getDish(int dishId);

}
