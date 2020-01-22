package dao;

import entity.users.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDAO {
    List<Employee> getAllEmployees(int emplId);
    public Employee getEmployeById(int employeeId);
    void deleteEmployeeById(int employeeId);
    void create(Employee employee);
    boolean updateEmployee(Employee employee);
    Employee getFreeEmployee(String position);
    Employee getEmployeeByEmail(String emailToCheck);

}
