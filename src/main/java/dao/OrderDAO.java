package dao;

import dao.dataSourse.DataSource;
import entity.order.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderDAO {//check statements!
    private static Logger logger = Logger.getLogger(OrderDAO.class.getName());
    private final  String INSERT_ORDER = "insert into orders (client_id_fk, order_status, " +
            "waiter_id_fk, cook_id_fk, ordered_table_fk, invoice, date_of_order) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final String GET_ALL_ORDERS = "SELECT * FROM orders where client_id_fk = ?;";
    private final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?;";

    public void create(Order order, int clientId, int waiterId, int cookId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER)){
            preparedStatement.setInt(1, clientId);
            preparedStatement.setString(2, order.getOrderStatus().toString());
            preparedStatement.setInt(3, waiterId);
            preparedStatement.setInt(4, cookId);
            preparedStatement.setInt(5, order.getTable().getId());
            preparedStatement.setDouble(6, order.getInvoice());
            preparedStatement.setLong(7, order.getTimestamp());
            preparedStatement.executeUpdate();
            logger.info("order is added");
        } catch (SQLException ex) {
            logger.info("order is not added, something goes wrong");
            ex.printStackTrace();
        }
    }

    public Order getById(int id){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Order resultOrder = new Order();
            while (resultSet.next()) {
                resultOrder.setId(resultSet.getInt("id"));
                resultOrder.setTimestamp(resultSet.getLong("date_of_order"));
                resultOrder.setInvoice(resultSet.getFloat("invoice"));
                resultOrder.setOrderStatus(resultSet.getString("order_status"));
                // extract Table table, List<DishesInOrder> dishes, Employee waiterToService, Employee cookToService
            }
            return resultOrder;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void remove(Order order){

    }

    public List<Order> getAll(int clientId){

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS)){
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order tempOrder = new Order();
                tempOrder.setId(resultSet.getInt("id"));
                tempOrder.setTimestamp(resultSet.getLong("date_of_order"));
                tempOrder.setInvoice(resultSet.getFloat("invoice"));
                tempOrder.setOrderStatus(resultSet.getString("order_status"));
                logger.info("order id: " + tempOrder.getId());
                // extracting Table table, List<DishesInOrder> dishes, Employee waiterToService, Employee cookToService
                // use JOIN

                orders.add(tempOrder);
            }
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void get(int id){


    }

    public void update(){

    }

}
