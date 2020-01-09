package dao;

import entity.users.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmployees(int emplId);
    void deleteEmployeeById(int employeeId);
    void create(Employee employee);
    boolean changeLoadFactor(Employee employee);
    Employee getFreeEmployee(String position);
    Employee getEmployeeByEmail(String emailToCheck);

}
