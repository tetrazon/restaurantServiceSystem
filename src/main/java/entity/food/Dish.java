package entity.food;

import entity.enumeration.FoodCategory;

public class Dish {

    private String name;
    private double price;
    private FoodCategory foodCategory;
    private String description;

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public Dish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    private void setPrice(float price) {
        this.price = price;
    }
}
