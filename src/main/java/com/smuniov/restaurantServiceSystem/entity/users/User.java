package com.smuniov.restaurantServiceSystem.entity.users;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull
    @Email
    @Size(min=2, max=30)
    private String email;
    @NotNull
    @Size(min=3, max=30)
    private String password;
    @NotNull
    @Size(min=1, max=20)
    private String name;
    @NotNull
    @Size(min=1, max=20)
    private String surname;
    private long created;

    public User(){

    }

    public User(int id){
        this.id = id;
    }

    public User(String email, String name, String surname, long created){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.created = created;
    }

    public User(int id, String email, String name, String surname, long created){
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.created = created;
    }

    public User(String email, String password, String name, String surname, long created) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getPassword() {
        return password;
    }
}
