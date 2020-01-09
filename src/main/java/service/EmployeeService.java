package service;

import dao.EmployeeDAO;
import dao.hibernate.EmployeeDAOHibernate;
import dao.jdbc.EmployeeDAOJdbc;
import entity.users.Employee;

import java.util.List;

public class EmployeeService {
    private static EmployeeDAO employeeDAO = new EmployeeDAOHibernate();

    public String getPasswordByEmail(String emailToCheck){

       Employee employee = employeeDAO.getEmployeeByEmail(emailToCheck);
       if(employee == null){
           return null;
       }
       return employee.getPassword();
    }

    public Integer getIdByEmail(String emailToCheck){
        return employeeDAO.getEmployeeByEmail(emailToCheck).getId();
    }

    public void deleteEmployeeById(int employeeId){
        //if load factor != 0 not delete
        employeeDAO.deleteEmployeeById(employeeId);
    }

    public List<Employee> getAllEmployees(int id){
        return employeeDAO.getAllEmployees( id);
    }

    public void create(Employee employee){
        employeeDAO.create(employee);
    }
}
