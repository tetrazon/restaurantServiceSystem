package com.smuniov.restaurantServiceSystem.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.smuniov.restaurantServiceSystem.DTO.Transfer.ClientDataAccess;
import com.smuniov.restaurantServiceSystem.DTO.Transfer.UserDataAccess;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.entity.users.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO <T extends User>{
    @JsonView(UserDataAccess.class)
    private int id;
    //@JsonView(UserDataAccess.class)
    private String email;
    //@JsonView(UserDataAccess.class)
    private String name;
    //@JsonView(UserDataAccess.class)
    private String surname;
    @JsonView(UserDataAccess.class)
    private long created;
    @JsonView(UserDataAccess.class)
    private String position;
    @JsonView(ClientDataAccess.class)
    private double deposit;

    public UserDTO(){
    }

    public UserDTO(T user) throws ClassCastException{
        id = user.getId();
        email = user.getEmail();
        name = user.getName();
        surname = user.getSurname();
        created = user.getCreated();
        if(user.getClass().equals(com.smuniov.restaurantServiceSystem.entity.users.Client.class)){
            position = "CLIENT";
            com.smuniov.restaurantServiceSystem.entity.users.Client client =
                    (com.smuniov.restaurantServiceSystem.entity.users.Client) user;
            deposit = client.getDeposit();
        } else {
            Employee employee = (Employee) user;
            position = employee.getPosition();
        }
    }

    public List<UserDTO> toDTO(List<T> users){
        List<UserDTO> usersDTO = new ArrayList<>();
        for (int i = 0; i < users.size(); i++){
            usersDTO.add(new UserDTO(users.get(i)));
        }
        return usersDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
