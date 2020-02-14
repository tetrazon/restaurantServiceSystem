package com.smuniov.restaurantServiceSystem.entity.order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@javax.persistence.Table(name = "tables_in_restaurant")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "seats")
    private  int seats;
    @Column(name = "is_reserved")
    private boolean isReserved;

    public Table(){
    }

    public Table(int seats, boolean isReserved) {
        this.seats = seats;
        this.isReserved = isReserved;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean getIsReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }


}
