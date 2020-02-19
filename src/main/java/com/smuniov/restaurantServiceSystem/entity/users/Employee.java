package com.smuniov.restaurantServiceSystem.entity.users;

import com.smuniov.restaurantServiceSystem.entity.enumeration.Position;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "employees")
public class Employee extends User {
    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;
    @Column(name = "load_factor")
    @Min(0)
    @Max(5)
    private int loadFactor; //0-5, 5 means busy

    public Employee(){
        super();
    }

    public Employee(String position){
       setPosition(position);

    }

    public Employee(String email, String password, String name, String surname, long created) {
        super(email, password, name, surname, created);
    }

    public String getPosition() {
        return position.name();
    }

    public void setPosition(String position) {
        this.position = Position.valueOf(position.toUpperCase());
    }

    public int getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }

    public void processOrder() throws InterruptedException {//imitation of processing
        if(position.toString().equalsIgnoreCase("COOK")){
            Thread.sleep(500);
        } else {
            Thread.sleep(200);
        }
    }
}
