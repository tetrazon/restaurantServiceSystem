package entity.people;

import entity.order.Order;
import java.util.LinkedList;
import java.util.List;

public class Client extends Person {

    private double deposit;
    private List<Order> orders;


    public Client(String email, String password, String name, String surname, long created) {
        super(email, password, name, surname, created);
        orders = new LinkedList<>();
        deposit = 0;
    }

    public Client(String email, String name, String surname, long created){
        super(email, name, surname, created);
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

