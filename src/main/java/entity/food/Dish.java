package entity.food;

import entity.enumeration.FoodCategory;

public class Dish {
    int id;
    private String name;
    private double price;
    private FoodCategory foodCategory;
    private String description;

    public Dish(){}

    public Dish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodCategory() {
        return foodCategory.name();
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = FoodCategory.valueOf(foodCategory.toUpperCase());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

}
