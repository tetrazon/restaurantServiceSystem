package dao;

import dao.dataSourse.DataSource;
import entity.users.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientDAO{
    private static Logger logger = LoggerFactory.getLogger(ClientDAO.class);
    private final String createClient = "insert into clients (name, surname, email, password, created)" +
            " VALUES (?, ?, ?, ?, ?);";
    private final String removeClientById = "delete from clients where id = ?;";
    private final String getAllClients = "SELECT * FROM clients;";
    private final String passwordByEmail = "SELECT password FROM clients WHERE email = ?;";
    private final String iDByEmail = "SELECT id FROM clients WHERE email = ?;";
    private final String CHECK_DEPOSIT = "select deposit from clients where id = ?";
    private final String UPDATE_DEPOSIT = "update clients set deposit = ?  where id = ?";

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

    public void deleteClientById(int id) {
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
                tempClient.setDeposit(resultSet.getDouble("deposit"));
                logger.info("extract Client, id = " + tempClient.getId() + "; deposit$: " + tempClient.getDeposit());
                clients.add(tempClient);
            }
            Collections.sort(clients, Comparator.comparing(Client::getId));
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
             PreparedStatement preparedStatement = connection.prepareStatement(iDByEmail)){
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

    public Double getClientDeposit(int clientId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_DEPOSIT)){
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Double deposit = null;
            while (resultSet.next()) {
                deposit = resultSet.getDouble("deposit");
            }
            if(deposit == null){
                logger.error("no such user!");
                return null;
            }
            return deposit;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void updateClientDeposit(int clientId, double newDeposit){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEPOSIT)){
            preparedStatement.setDouble(1, newDeposit);
            preparedStatement.setInt(2, clientId);
            preparedStatement.executeUpdate();
            logger.info("client with id: "+ clientId + ", deposit is updated: " + newDeposit + "$");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
