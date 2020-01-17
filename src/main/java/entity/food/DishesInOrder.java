package entity.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.order.Order;

import javax.persistence.*;
@Entity
@Table(name = "dishes_in_order")
public class DishesInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id_fk")
    @JsonIgnore
    private Order order;
    //@EmbeddedId
    //@AttributeOverride(name="id", column = @Column(name="dish_id_fk"))
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id_fk")
    private Dish dish;
    @Column(name = "quantity")
    int quantity;

    public DishesInOrder(){}

    public DishesInOrder(Dish dish){
        this.dish = dish;
    }

    public DishesInOrder(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public DishesInOrder(Order order){
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public Dish getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
