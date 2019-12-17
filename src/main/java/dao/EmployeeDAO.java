package dao;

import dao.dataSourse.DataSource;
import entity.people.Employee;
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
    private final  String INSERT_EMPLOYEE = "insert into employees (email, password, name, surname, created, load_factor, position) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final String GET_ALL_AVAIL_EMPL = "SELECT * FROM employees where employees.position = ? and load_factor < 5;";
    private final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?;";
    private final String SET_LOAD = "update employees set load_factor = ((select load_factor from employees where id = ?) + ?) where id = ?";
    private final String PASS_BY_EMAIL = "SELECT password FROM employees WHERE email = ?;";
    private final String ID_BY_EMAIL = "SELECT id FROM employees WHERE email = ?;";

    public void create(Employee employee){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE)){
            preparedStatement.setString(1, employee.getEmail());
            preparedStatement.setString(2, employee.getPassword());
            preparedStatement.setString(3, employee.getName());
            preparedStatement.setString(4, employee.getSurname());
            preparedStatement.setLong(5, employee.getCreated());
            preparedStatement.setInt(6, employee.getLoadFactor());
            preparedStatement.setString(7, employee.getPosition());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean changeLoadFactor (int load, int epmloyeeId){
        if (load > 1 || load <-1)
        {
            logger.error("wrong load");
            return false;
        }

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_LOAD)){
            preparedStatement.setInt(1, epmloyeeId);
            preparedStatement.setInt(2, load);
            preparedStatement.setInt(3, epmloyeeId);
            preparedStatement.executeUpdate();
            logger.info("employee load factor has bee changed, id:" + epmloyeeId + ", load: " + load);
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
