package dao;

import entity.food.Dish;

import java.util.List;

public interface DishDAO {
    void deleteDishById(int dishId);
    void updateDish(Dish dish);
    void create(Dish dish);
    List<Dish> getAllDishes();
    Dish getDishById(int dishId);
}
