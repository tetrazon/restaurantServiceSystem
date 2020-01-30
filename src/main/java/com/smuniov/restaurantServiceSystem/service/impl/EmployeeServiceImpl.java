package com.smuniov.restaurantServiceSystem.service.impl;

import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.repository.EmployeeRepository;
import com.smuniov.restaurantServiceSystem.service.EmployeeServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeServiceI {

    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    public List getAll() {
        return employeeRepository.findAllByOrderByLoadFactorAsc();
    }

    @Override
    public Optional findById(int employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public void deleteById(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public void create(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public boolean update(Employee employee) {
        if(employee == null){
            return false;
        }
        employeeRepository.saveAndFlush(employee);
        return true;
    }

    @Override
    public Employee getFree(String position) {
        return (Employee) employeeRepository.findAllByOrderByLoadFactorAsc().get(0);
    }

    @Override
    public Employee getByEmail(String emailToCheck) {
        return employeeRepository.findByEmail(emailToCheck);
    }
}
