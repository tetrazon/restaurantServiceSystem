package service;

import dao.EmployeeDAO;
import entity.users.Employee;

import java.util.List;

public class EmployeeService {
    private static EmployeeDAO employeeDAO = new EmployeeDAO();

    public String getPasswordByEmail(String emailToCheck){
       return employeeDAO.getPasswordByEmail(emailToCheck);
    }

    public Integer getIdByEmail(String emailToCheck){
        return employeeDAO.getIdByEmail(emailToCheck);
    }

    public void deleteEmployeeById(int employeeId){
        //if load factor != 0 not delete
        employeeDAO.deleteEmployeeById(employeeId);
    }

    public List<Employee> getAllEmployees(){
        return employeeDAO.getAllEmployees();
    }

    public void create(Employee employee){
        employeeDAO.create(employee);
    }
}
