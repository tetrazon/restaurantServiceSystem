package dao.jdbc;

import dao.ClientDAO;
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

public class ClientDAOJdbc implements ClientDAO {
    private static Logger logger = LoggerFactory.getLogger(ClientDAOJdbc.class);
    private final String createClient = "insert into clients (name, surname, email, password, created)" +
            " VALUES (?, ?, ?, ?, ?);";
    private final String removeClientById = "delete from clients where id = ?;";
    private final String getAllClients = "SELECT * FROM clients;";
    private final String clientByEmail = "SELECT * FROM clients WHERE email = ?;";
    private final String GET_CLIENT_BY_ID = "select * from clients where id = ?";
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
                Client tempClient = getClient(resultSet);
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

    public Client getClientByEmail(String emailToCheck){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(clientByEmail)){
            preparedStatement.setString(1, emailToCheck);
            ResultSet resultSet = preparedStatement.executeQuery();
            //String result = null;
            Client client = null;
            while (resultSet.next()) {
                if(resultSet.getString("password") == null){
                    logger.error("no such user!");
                    return null;
                }
                client = getClient(resultSet);
            }
            
            return client;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Client getClientById(int clientId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_BY_ID)){
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Double deposit = null;
            Client client = null;
            while (resultSet.next()) {
                if(resultSet.getString("email") == null){
                    logger.error("no such user!");
                    return null;
                }
                client = getClient(resultSet);
                //deposit = resultSet.getDouble("deposit");

            }

            return client;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Client getClient(ResultSet resultSet) throws SQLException {
        Client client;
        client = new Client();
        client.setId(resultSet.getInt("id"));
        client.setName(resultSet.getString("name"));
        client.setEmail(resultSet.getString("email"));
        client.setPassword(resultSet.getString("password"));
        client.setDeposit(resultSet.getDouble("deposit"));
        client.setSurname(resultSet.getString("surname"));
        client.setCreated(resultSet.getLong("created"));
        return client;
    }

    public void updateClientDeposit(Client client){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEPOSIT)){
            preparedStatement.setDouble(1, client.getDeposit());
            preparedStatement.setInt(2, client.getId());
            preparedStatement.executeUpdate();
            logger.info("client with id: "+ client.getId() + ", deposit is updated: " + client.getDeposit() + "$");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
