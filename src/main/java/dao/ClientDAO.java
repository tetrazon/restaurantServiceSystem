package dao;

import dao.dataSourse.DataSource;
import entity.people.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO{
    private final String createClient = "insert into clients (name, surname, email, password, created)" +
            " VALUES (?, ?, ?, ?, ?);";
    private final String removeClient = "delete from clients where email = ?;";
    private final String removeClientById = "delete from clients where id = ?;";
    private final String getAllClients = "SELECT * FROM clients;";

    public void create(Client client) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createClient)){
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPassword());
            preparedStatement.setLong(5, client.getCreated());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Client client) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeClient)){
            preparedStatement.setString(1, client.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(int id) {//add ON DELETE CASCADE in tables?
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeClientById)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Client> getAll(){

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllClients)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                clients.add(new Client(resultSet.getString("email"),resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getLong("created")));
            }
            return clients;
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
