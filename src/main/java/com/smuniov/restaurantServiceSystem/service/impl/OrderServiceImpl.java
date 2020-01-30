package com.smuniov.restaurantServiceSystem.service.impl;

import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.repository.DishesInOrderRepository;
import com.smuniov.restaurantServiceSystem.repository.OrderRepository;
import com.smuniov.restaurantServiceSystem.repository.TableRepository;
import com.smuniov.restaurantServiceSystem.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderServiceI {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishesInOrderRepository dishesInOrderRepository;

    @Autowired
    private TableRepository tableRepository;


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

}
