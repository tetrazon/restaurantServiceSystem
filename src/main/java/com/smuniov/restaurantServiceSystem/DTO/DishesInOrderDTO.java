package com.smuniov.restaurantServiceSystem.DTO;

import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;

import java.util.ArrayList;
import java.util.List;

public class DishesInOrderDTO {
    private int id;
    private int dishId;
    private int orderId;
    private String dishName;
    private double price;
    private int quantity;

    public DishesInOrderDTO(){
    }

    public DishesInOrderDTO(DishesInOrder dishesInOrder){
        id = dishesInOrder.getId();
        dishName = dishesInOrder.getDish().getName();
        price = dishesInOrder.getDish().getPrice();
        quantity = dishesInOrder.getQuantity();
        dishId = dishesInOrder.getDish().getId();
        orderId = dishesInOrder.getOrder().getId();
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    static public List<DishesInOrderDTO> toDTO(List<DishesInOrder> dishesInOrderList){
        List<DishesInOrderDTO> dishesInOrderDTOList = new ArrayList<>();
        for (int i = 0; i < dishesInOrderList.size(); i++) {
            dishesInOrderDTOList.add(new DishesInOrderDTO(dishesInOrderList.get(i)));
        }
        return dishesInOrderDTOList;
    }

}
