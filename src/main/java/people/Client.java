package people;

import entity.order.Order;
import java.util.LinkedList;

public class Client extends Person {

    private double deposit;
    private LinkedList<Order> orders;

    public Client(String email, String password, String name, String surname, long created) {
        super(email, password, name, surname, created);
        orders = new LinkedList<>();
        deposit = 0;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public LinkedList<Order> getOrders() {
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

