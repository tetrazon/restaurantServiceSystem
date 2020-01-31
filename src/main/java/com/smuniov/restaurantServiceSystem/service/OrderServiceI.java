package com.smuniov.restaurantServiceSystem.service;

import com.smuniov.restaurantServiceSystem.DTO.DishesInOrderDTO;
import com.smuniov.restaurantServiceSystem.entity.food.Dish;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;

import java.util.List;
import java.util.Optional;

public interface OrderServiceI {
    List findAllOrders(Client client);
    List getAllByClient_IdOrderByTimestamp(int id);
    List<DishesInOrder> findAllDishesInOrderByOrder_Id(int orderId);
    List<Table> getAllTables();
    List<Table> getAllTablesByReserved(boolean isReserved);
    void changeTableStatus(Table table);
    Table findTableById(int id);
    boolean addDishesInOrder(List<DishesInOrderDTO> dishesInOrderDTOList);
    boolean orderInit(Client client, List<DishesInOrderDTO> dishesInOrderDTOList);
    double checkDeposit(Client client, List<DishesInOrderDTO> dishesInOrderDTOList);
    void bookTable(int tableId, Order order);
    Order getOrderById (int id);
    void processOrder(int orderId, Employee waiter, Employee cook);
    void finishOrder(Order order);
    public void addSaveUpdateDish(Dish dish);
    public List<Dish> getAllDishes();
    public void deleteDish(int dishId);
    public Optional getDish(int dishId);

}
