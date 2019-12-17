package service;

import dao.EmployeeDAO;

public class EmployeeService {
    private static EmployeeDAO employeeDAO = new EmployeeDAO();

    public String getPasswordByEmail(String emailToCheck){
       return employeeDAO.getPasswordByEmail(emailToCheck);
    }

    public Integer getIdByEmail(String emailToCheck){
        return employeeDAO.getIdByEmail(emailToCheck);
    }
}
