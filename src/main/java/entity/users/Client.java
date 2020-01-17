package entity.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.order.Order;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
@Entity
//@Transactional
@Table(name = "clients")
public class Client extends User {
    @Column(name = "deposit")
    private double deposit;
    @OneToMany(mappedBy = "client")
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    //@OneToMany(mappedBy = "client", fetch=FetchType.LAZY)
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

