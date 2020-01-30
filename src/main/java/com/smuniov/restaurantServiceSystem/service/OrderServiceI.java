package com.smuniov.restaurantServiceSystem.service;

import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Client;

import java.util.List;

public interface OrderServiceI {
    List findAllOrders(Client client);
    List getAllByClient_IdOrderByTimestamp(int id);
    List<DishesInOrder> findAllDishesInOrderByOrder_Id(int orderId);
    List<Table> getAllTables();
    List<Table> getAllTablesByReserved(boolean isReserved);

}
