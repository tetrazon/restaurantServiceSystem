package сontroller.db_controller;

import connectionPool.ConnectionPool;
import people.Client;
import сontroller.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PgControllerWithConnPool implements Controller {

    @Override
    public void addClient(Client client) {

    }

    @Override
    public void deleteClient(Client client) {

    }

    @Override
    public List<Client> showAllClients() {
        return null;
    }

    public void getAllUsers(){
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM clients;")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void changeClientParams(Client client) {

    }
}
