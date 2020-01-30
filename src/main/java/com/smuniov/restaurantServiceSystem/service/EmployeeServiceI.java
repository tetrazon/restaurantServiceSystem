package com.smuniov.restaurantServiceSystem.service;

import com.smuniov.restaurantServiceSystem.entity.users.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeServiceI {
    List getAll();
    Optional findById(int employeeId);
    void deleteById(int employeeId);
    void create(Employee employee);
    boolean update(Employee employee);
    Employee getFree(String position);
    Employee getByEmail(String emailToCheck);

}
