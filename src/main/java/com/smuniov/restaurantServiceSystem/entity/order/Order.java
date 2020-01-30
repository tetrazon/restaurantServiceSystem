package com.smuniov.restaurantServiceSystem.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.entity.enumeration.OrderStatus;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;
    @Column(name = "date_of_order")
    private Long timestamp;
    @ManyToOne(fetch=FetchType.LAZY)//cascade = {CascadeType.ALL}
    @JoinColumn(name = "ordered_table_fk")
    @JsonIgnore
    private Table table;
    @OneToMany(mappedBy = "order", fetch=FetchType.LAZY) //, fetch=FetchType.LAZY //cascade = {CascadeType.ALL}
    //@LazyCollection(LazyCollectionOption.FALSE)
    //@LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    private List<DishesInOrder> dishes;

//    @OneToMany(mappedBy = "dishWithPrice", fetch=FetchType.LAZY)
//    private List<Dish> foodsWithPrice;

    @Column(name = "invoice")
    private Double invoice;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    //@JoinColumn(name = "order_status")
    private OrderStatus orderStatus;
    @ManyToOne(fetch=FetchType.LAZY)//cascade = {CascadeType.ALL}
    @JsonIgnore
    @JoinColumn(name = "waiter_id_fk")
    private Employee waiter;
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "cook_id_fk")
    private Employee cook;
    @ManyToOne (fetch=FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "client_id_fk")
    private Client client;

    public Order (){
        invoice = 0.;
        timestamp = 0l;
        dishes = new ArrayList<>();
    }

//    public Order(List<Dish> foodsWithPrice) {
//        this.foodsWithPrice = foodsWithPrice;
//        invoice = 0;
//        dishes = new ArrayList<>();
//    }

    public Order(int orderId){
        id = orderId;
    }

    public Order(int id, long timestamp, Table table, List<DishesInOrder> dishes,
                 double invoice, OrderStatus orderStatus, Employee waiter, Employee cookToService, Client client ){
        this.id = id;
        this.timestamp = timestamp;
        this.table = table;
        this.dishes = dishes;
        this.invoice = invoice;
        this.orderStatus = orderStatus;
        this.waiter = waiter;
        this.cook = cookToService;
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDishes(List<DishesInOrder> dishes) {
        this.dishes = dishes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<DishesInOrder> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishesInOrder> dishes) {
        this.dishes = dishes;
    }

    public double getInvoice() {
        return invoice;
    }

    public void setInvoice(double invoice) {
        this.invoice = invoice;
    }

    public String getOrderStatus() {
        return orderStatus.toString();
    }

    public void setOrderStatus(String  orderStatus) {
        this.orderStatus = OrderStatus.valueOf(orderStatus);
    }

    public Employee getCook() {
        return cook;
    }

    public void setCook(Employee cook) {
        this.cook = cook;
    }

    public Employee getWaiter() {
        return waiter;
    }

    public void setWaiter(Employee waiter) {
        this.waiter = waiter;
    }

    public void finishOrder(){
        timestamp = (System.currentTimeMillis());
    }

    public static double calculateResultSum(List<DishesInOrder> dishes){
        float sum = 0;
        for (DishesInOrder dishesInOrder : dishes
             ) {
            sum += dishesInOrder.getQuantity() * dishesInOrder.getDish().getPrice();
        }
        return sum;
    }
}


