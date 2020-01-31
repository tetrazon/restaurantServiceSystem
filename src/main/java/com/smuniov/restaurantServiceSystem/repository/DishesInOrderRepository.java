package com.smuniov.restaurantServiceSystem.repository;

import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DishesInOrderRepository extends JpaRepository<DishesInOrder, Integer> {
    List<DishesInOrder> findAllDishesInOrderByOrder_Id(int orderId);
}
