package dao;

import dao.dataSourse.DataSource;
import entity.users.Employee;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeDAO {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeDAO.class);
    private final  String INSERT_EMPLOYEE = "insert into employees (email, password, name, surname, created, position) "
            + "VALUES (?, ?, ?, ?, ?, ?);";
    private final String GET_ALL_AVAIL_EMPL = "SELECT * FROM employees where employees.position = ? and load_factor < 5;";
    private final String SET_LOAD = "update employees set load_factor = ((select load_factor from employees where id = ?) + ?) where id = ?";
    private final String PASS_BY_EMAIL = "SELECT password FROM employees WHERE email = ?;";
    private final String ID_BY_EMAIL = "SELECT id FROM employees WHERE email = ?;";
    private final String DELETE_EMPLOYEE_BY_ID = "delete from employees where id = ?";
    private final String GET_ALL_EMPLOYEES = "select * from employees where id!= ?;";

    public List<Employee> getAllEmployees(int emplId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_EMPLOYEES)){
            preparedStatement.setInt(1, emplId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()){
                Employee tempEmployee = new Employee();
                tempEmployee.setName(resultSet.getString("name"));
                tempEmployee.setId(resultSet.getInt("id"));
                tempEmployee.setEmail(resultSet.getString("email"));
                tempEmployee.setPosition(resultSet.getString("position"));
                logger.info("extracted empl position: " + tempEmployee.getPosition());
                employees.add(tempEmployee);
            }
            Collections.sort(employees, Comparator.comparing(Employee::getId));
            return employees;
        } catch (SQLException ex) {
            logger.error("connection error");
            ex.printStackTrace();
        }
        return null;
    }

    public void deleteEmployeeById(int employeeId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID)){
            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();
            logger.info("employee with id " + employeeId + " is deleted");
        } catch (SQLException ex) {
            logger.error("connection error");
            ex.printStackTrace();
        }
    }

    public void create(Employee employee){ //(email, password, name, surname, created, position)
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE)){
            logger.info("position of inserting empl: " + employee.getPosition()+ "\n"
             + " mail: " + employee.getEmail()+ " pass: " + employee.getPassword() + " name " + employee.getName() +
                    " surn " + employee.getSurname());
            preparedStatement.setString(1, employee.getEmail());
            preparedStatement.setString(2, employee.getPassword());
            preparedStatement.setString(3, employee.getName());
            preparedStatement.setString(4, employee.getSurname());
            preparedStatement.setLong(5, System.currentTimeMillis());
            preparedStatement.setString(6, employee.getPosition());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean changeLoadFactor (int load, int employeeId){
        if (load > 1 || load <-1)
        {
            logger.error("wrong load");
            return false;
        }

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_LOAD)){
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, load);
            preparedStatement.setInt(3, employeeId);
            preparedStatement.executeUpdate();
            logger.info("employee load factor has bee changed, id:" + employeeId + ", load: " + load);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Employee getFreeEmployee(String position){//check position

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_AVAIL_EMPL)){
            preparedStatement.setString(1, position.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                Employee employee = new Employee(position.toUpperCase());
                employee.setLoadFactor(resultSet.getInt("load_factor"));
                employee.setId(resultSet.getInt("id"));
                employee.setName("name");
                logger.info("extract employee, id = " + employee.getId() + "status = " + employee.getPosition());
                employees.add(employee);
            }
            Collections.sort(employees, Comparator.comparingInt(Employee::getLoadFactor));
            return employees.get(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getPasswordByEmail(String emailToCheck){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PASS_BY_EMAIL)){
            preparedStatement.setString(1, emailToCheck);
            ResultSet resultSet = preparedStatement.executeQuery();
            String result = null;
            while (resultSet.next()) {
                result = resultSet.getString("password");
            }
            if(result == null){
                logger.error("no such user!");
                return null;
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Integer getIdByEmail(String emailToCheck){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ID_BY_EMAIL)){
            preparedStatement.setString(1, emailToCheck);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer result = null;
            while (resultSet.next()) {
                result = resultSet.getInt("id");
            }
            if(result == null){
                logger.error("no such user!");
                return null;
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
