package dao.db_controller;

import dao.connectionPool.DataSource;
import people.Client;
import сontroller.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PgControllerWithConnPool implements Controller {

    @Override
    public void registerClient(Client client) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into clients (name, surname, email, password, created) "
                     + "VALUES (?, ?, ?, ?, ?);")){
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPassword());
            preparedStatement.setLong(5, client.getCreated());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteClient(Client client) {

    }

    @Override
    public List<Client> showAllClients() {
        return null;
    }

    public void getAllUsers(){

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM clients;")){
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
