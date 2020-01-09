package dao;

import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import entity.users.Client;
import entity.users.Employee;

import java.util.List;

public interface OrderDAO {
    void insertDishesInOrder(DishesInOrder dishesInOrder);
    void createOrder(Order order);
    void setTimeOfOrder(Order order);
    boolean initOrder(Order order);
    boolean changeOrderStatus(Order order);
    Order getById(int id);
    Integer getOrderId (Client client, String orderStatus);
    List<Order> getAllOrders(Client client);
    boolean addEmployeesInOrder(Employee waiter, Employee cook, int orderId);
    List<Table> getAllTables();
    Table getTableById(int tableId);
    void setTableStatus(Table table);
    List<DishesInOrder> getDishesFromOrder(Order order);

}
