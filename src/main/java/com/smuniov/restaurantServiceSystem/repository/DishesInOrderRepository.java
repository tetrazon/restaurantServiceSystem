package com.smuniov.restaurantServiceSystem.repository;

import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishesInOrderRepository extends JpaRepository<DishesInOrder, Integer> {
    List<DishesInOrder> findAllDishesInOrderByOrder_Id(int orderId);
}
