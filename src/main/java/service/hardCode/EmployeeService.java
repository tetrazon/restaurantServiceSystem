package service.hardCode;

import dao.EmployeeDAO;
import entity.users.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EmployeeService {
    private EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
    }

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

    public Employee getEmployeeById(int employeeId){
        return employeeDAO.getEmployeById(employeeId);
    }

    public boolean updateEmployee(Employee employee) {
        return employeeDAO.updateEmployee(employee);
    }
}
