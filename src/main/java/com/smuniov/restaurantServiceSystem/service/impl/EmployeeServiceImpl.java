package com.smuniov.restaurantServiceSystem.service.impl;

import com.smuniov.restaurantServiceSystem.Exception.BadRequestException;
import com.smuniov.restaurantServiceSystem.entity.enumeration.Position;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.repository.ClientRepository;
import com.smuniov.restaurantServiceSystem.repository.EmployeeRepository;
import com.smuniov.restaurantServiceSystem.service.EmployeeServiceI;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeServiceI {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ClientRepository clientRepository) {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

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
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailLogged = principal.getName();
        if(employeeRepository.existsByEmail(emailLogged)){
            throw new BadRequestException("you cannot delete yourself!!!");
        }
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public void create(Employee employee) {
        String emailToCheck = employee.getEmail();
        logger.info("employee to add:" + emailToCheck + "; " + employee.getName());
        try{
            if(emailToCheck == null ||
                    employee.getPassword() == null ||
                    employee.getName() == null || employee.getSurname() == null){
                throw new BadRequestException("incorrect user data!");
            }
            if(!clientRepository.existsByEmail(emailToCheck) && !employeeRepository.existsByEmail(emailToCheck)){
                employee.setCreated(System.currentTimeMillis());
                employeeRepository.save(employee);
                logger.info("employee with email: " + emailToCheck + " is registered");
            } else {
                throw new BadRequestException("user with email " + employee.getEmail() + " is existed!");
            }} catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }
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
        return (Employee) employeeRepository.findAllByPositionOrderByLoadFactorAsc(Position.valueOf(position)).get(0);
    }

    @Override
    public Employee getByEmail(String emailToCheck) {
        return employeeRepository.findByEmail(emailToCheck);
    }
}
