package dao;

import dao.dataSourse.DataSource;
import entity.people.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClientDAO{
    private static Logger logger = Logger.getLogger(ClientDAO.class.getName());
    private final String createClient = "insert into clients (name, surname, email, password, created)" +
            " VALUES (?, ?, ?, ?, ?);";
    private final String removeClient = "delete from clients where email = ?;";
    private final String removeClientById = "delete from clients where id = ?;";
    private final String getAllClients = "SELECT * FROM clients;";
    private final String passwordByEmail = "SELECT password FROM clients WHERE email = ?;";
    private final String addSessionId = "INSERT INTO SESSIONS (session_id, client_email_fk) VALUES(?, ?);";

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
                Client tempClient = new Client();
                tempClient.setId(resultSet.getInt("id"));
                tempClient.setEmail(resultSet.getString("email"));
                tempClient.setName(resultSet.getString("name"));
                tempClient.setSurname(resultSet.getString("surname"));
                tempClient.setCreated(resultSet.getLong("created"));
                logger.info("extract Client, id = " + tempClient.getId());
                clients.add(tempClient);
            }
            return clients;
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getPasswordByEmail(String emailToCheck){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(passwordByEmail)){
            preparedStatement.setString(1, emailToCheck);
            ResultSet resultSet = preparedStatement.executeQuery();
            String result = null;
            while (resultSet.next()) {
                result = resultSet.getString("password");
            }
            if(result == null){
                logger.info("no such user!");
                return null;
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void addSessionId(String email, String sessionId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addSessionId)){
            preparedStatement.setString(1, sessionId);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
