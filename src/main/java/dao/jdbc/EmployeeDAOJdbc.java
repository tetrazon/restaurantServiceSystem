package dao.jdbc;

import dao.EmployeeDAO;
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

public class EmployeeDAOJdbc implements EmployeeDAO {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeDAOJdbc.class);
    private final  String INSERT_EMPLOYEE = "insert into employees (email, password, name, surname, created, position) "
            + "VALUES (?, ?, ?, ?, ?, ?);";
    private final String GET_ALL_AVAIL_EMPL = "SELECT * FROM employees where employees.position = ? and load_factor < 5;";
    private final String SET_LOAD = "update employees set load_factor = ? where id = ?";
    private final String EMPL_BY_EMAIL = "SELECT * FROM employees WHERE email = ?;";
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

    public void create(Employee employee){
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

    public boolean changeLoadFactor(Employee employee){


        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_LOAD)){
            preparedStatement.setInt(1, employee.getLoadFactor());
            preparedStatement.setInt(2, employee.getId());
            preparedStatement.executeUpdate();
            logger.info("employee load factor has bee changed, id:" + employee.getId() + ", load: " + employee.getLoadFactor());
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Employee getFreeEmployee(String position){

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
                logger.info("extract employee, id = " + employee.getId() + "; status = " +
                        employee.getPosition() + "; load is: " + employee.getLoadFactor());
                employees.add(employee);
            }
            Collections.sort(employees, Comparator.comparingInt(Employee::getLoadFactor));
            for(Employee e: employees){
                logger.info("LOAD" + e.getLoadFactor());
            }
            return employees.get(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByEmail(String emailToCheck){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EMPL_BY_EMAIL)){
            preparedStatement.setString(1, emailToCheck);
            ResultSet resultSet = preparedStatement.executeQuery();
            //String result = null;
            Employee employee = new Employee();
            while (resultSet.next()) {
                if(resultSet.getString("password") == null){
                    logger.error("no such user!");
                    return null;
                }
                //result = resultSet.getString("password");
                employee.setPassword(resultSet.getString("password"));
                employee.setPosition(resultSet.getString("position"));
                employee.setLoadFactor(resultSet.getInt("load_factor"));
                employee.setId(resultSet.getInt("id"));
                employee.setName("name");
                employee.setCreated(resultSet.getLong("created"));

                employee.setSurname(resultSet.getString("surname"));
                employee.setEmail(resultSet.getString("email"));
                logger.info("extract employee, id = " + employee.getId() + "; email is: " + employee.getEmail());
                return employee;
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
