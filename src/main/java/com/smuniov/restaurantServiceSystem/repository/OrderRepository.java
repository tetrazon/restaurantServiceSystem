package com.smuniov.restaurantServiceSystem.repository;

import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
//    void insertDishesInOrder(DishesInOrder dishesInOrder);
//    void createOrder(Order order);
//    void setTimeOfOrder(Order order);
//    boolean initOrder(Order order);
//    boolean changeOrderStatus(Order order);
//    Order getById(int id);
//    //Integer getOrderId (ClientDataAccess client, String orderStatus);
//    List<Order> getAllOrders(ClientDataAccess client);
    List getAllByClientOrderByTimestampAsc(Client client);
    List getAllByClient_IdOrderByTimestamp(int id);
//    boolean addEmployeesInOrder(Employee waiter, Employee cook, int orderId);
//    List<Table> getAllTables();
//    Table getTableById(int tableId);
//    void setTableStatus(Table table);
//    List<DishesInOrder> getDishesFromOrder(Order order);
}
