package dao;

import dao.dataSourse.DataSource;
import entity.people.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class EmployeeDAO {
//
    private static Logger logger = Logger.getLogger(EmployeeDAO.class.getName());
    private final  String INSERT_EMPLOYEE = "insert into employees (email, password, name, surname, created, current_status, position) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final String GET_ALL_ORDERS = "SELECT * FROM orders where client_id_fk = ?;";
    private final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?;";

    public void create(Employee employee){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE)){
            preparedStatement.setString(1, employee.getEmail());
            preparedStatement.setString(2, employee.getPassword());
            preparedStatement.setString(3, employee.getName());
            preparedStatement.setString(4, employee.getSurname());
            preparedStatement.setLong(5, employee.getCreated());
            //preparedStatement.setString(6, employee.get);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
