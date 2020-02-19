package com.smuniov.restaurantServiceSystem.entity.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smuniov.restaurantServiceSystem.entity.order.Order;

import javax.persistence.Entity;



import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
@Entity
@Table(name = "clients")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client extends User {
    @Column(name = "deposit")
    private double deposit;
    @OneToMany(mappedBy = "client", fetch=FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    public Client(){
        super();
    }

    public Client(int id){
        super(id);
    }

    public Client(String email, String password, String name, String surname, long created) {
        super(email, password, name, surname, created);
        orders = new LinkedList<>();
        deposit = 100.;
    }

    public Client(String email, String name, String surname, long created){

        super(email, name, surname, created);
        deposit = 100.;
    }

    public Client(int id, String email, String name, String surname, long created){
        super(id, email, name, surname, created);
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(LinkedList<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void removeOrder(Order order){
        orders.remove(order);
    }

}

