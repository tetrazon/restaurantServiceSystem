package entity.food;

import entity.enumeration.FoodCategory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@Embeddable
@Table(name = "dishes")
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

    @OneToMany(mappedBy = "dish")
    private List<DishesInOrder> dishesInOrders;

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
