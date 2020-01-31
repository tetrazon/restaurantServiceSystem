package com.smuniov.restaurantServiceSystem.entity.food;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.smuniov.restaurantServiceSystem.entity.enumeration.FoodCategory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@Embeddable
@Table(name = "dishes")
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "$$_hibernate_interceptor"})
public class Dish  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "food_category")
    private FoodCategory foodCategory;
    @Column(name = "description")
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "dish")//, cascade = {CascadeType.ALL}
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<DishesInOrder> dishesInOrders;

    public Dish(){}

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public List<DishesInOrder> getDishesInOrders() {
        return dishesInOrders;
    }

    public void setDishesInOrders(List<DishesInOrder> dishesInOrders) {
        this.dishesInOrders = dishesInOrders;
    }

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
