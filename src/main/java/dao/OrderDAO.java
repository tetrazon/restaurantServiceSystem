package dao;

import dao.dataSourse.DataSource;
import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {//check statements!

    private static Logger logger = LoggerFactory.getLogger(OrderDAO.class);
    private final  String INSERT_ORDER = "insert into orders (client_id_fk, order_status, " +
            "waiter_id_fk, cook_id_fk, ordered_table_fk, invoice, date_of_order) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final  String INIT_ORDER = "insert into orders (client_id_fk, ordered_table_fk, order_status, invoice) VALUES (?,?,?,?);";
    private final String GET_ALL_ORDERS = "SELECT * FROM orders where client_id_fk = ?;";
    private final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?;";
    private final String GET_ORDER_ID = "SELECT id FROM orders WHERE client_id_fk = ? and order_status = ?;";//check
    private final String ADD_EMPL_IN_ORDER = "update orders set waiter_id_fk = ?, cook_id_fk = ? where id = ?";
    private final String GET_AVALIABLE_TABLES = "SELECT * FROM tables_in_restaurant where is_reserved = 'false';";
    private final String GET_TABLE_BY_ID = "SELECT * FROM tables_in_restaurant where id = ?;";
    private final String SET_TABLE_STATUS = "update tables_in_restaurant set is_reserved = ? where id = ?";
    private final String CHANGE_ORDER_STATUS = "update orders set order_status = ? where id = ?";
    private final String SET_ORDER_TIME = "update orders set date_of_order = ? where orders.id = ?";
    private final String INSERT_DISHES_IN_ORDER = "insert into dishes_in_order (dish_id_fk, order_id_fk, quantity) VALUES (?,?,?)";

public void insertDishesInOrder(DishesInOrder dishesInOrder){
    try (Connection connection = DataSource.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DISHES_IN_ORDER)){
        preparedStatement.setInt(1, dishesInOrder.getDish().getId());
        preparedStatement.setInt(2, dishesInOrder.getOrderId());
        preparedStatement.setInt(3, dishesInOrder.getQuantity());
        preparedStatement.executeUpdate();
        logger.info("dishes in order is added");
    } catch (SQLException ex) {
        logger.info("dishes inorder are not added, db connection goes wrong");
        ex.printStackTrace();
    }
}

    public void createOrder(Order order){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER)){
            preparedStatement.setInt(1, order.getClientId());
            preparedStatement.setString(2, order.getOrderStatus());
            preparedStatement.setInt(3, order.getWaiterToService().getId());
            preparedStatement.setInt(4, order.getCookToService().getId());
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

    public void setTimeOfOrder(int orderId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ORDER_TIME)){
            long now = System.currentTimeMillis();
            preparedStatement.setLong(1, now);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
            logger.info("order time is set: " + now);
            logger.info("order id: " + orderId);
        } catch (SQLException ex) {
            logger.info("order time is not set, something goes wrong");
            ex.printStackTrace();
        }
    }

    public boolean initOrder(int clientId, int orderedTableId, double invoice){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INIT_ORDER)){
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, orderedTableId);
            preparedStatement.setString(3, "NEW");
            preparedStatement.setDouble(4, invoice);
            preparedStatement.executeUpdate();
            logger.info("order is initialized, order status is NEW");
            return true;
        } catch (SQLException ex) {
            logger.error("order is not added, something goes wrong");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean changeOrderStatus(int orderId, String status){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_ORDER_STATUS)){
            preparedStatement.setString(1, status.toUpperCase());
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
            logger.info("order is changed, order status is" + status);
            return true;
        } catch (SQLException ex) {
            logger.error("status is not changed, something goes wrong");
            ex.printStackTrace();
            return false;
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
                resultOrder.setInvoice(resultSet.getDouble("invoice"));
                resultOrder.setOrderStatus(resultSet.getString("order_status"));
                // extract Table table, List<DishesInOrder> dishes, Employee waiterToService, Employee cookToService
            }
            return resultOrder;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Integer getOrderId (int clientId, String orderStatus){//check input
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_ID)){
            preparedStatement.setInt(1, clientId);
            preparedStatement.setString(2, orderStatus.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer orderId = null;
            while (resultSet.next()) {
                orderId = resultSet.getInt("id");
            }
            if(orderId == null){
                logger.error("no such order!");
            }
            return orderId;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void remove(Order order){

    }

    public List<Order> getAllOrders(int clientId){

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS)){
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order tempOrder = new Order();
                tempOrder.setId(resultSet.getInt("id"));
                tempOrder.setTimestamp(resultSet.getLong("date_of_order"));
                tempOrder.setInvoice(resultSet.getDouble("invoice"));
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

    public boolean addEmployeesInOrder(int waiterId, int cookId, int orderId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_EMPL_IN_ORDER)){
            preparedStatement.setInt(1, waiterId);
            preparedStatement.setInt(2, cookId);
            preparedStatement.setInt(3, orderId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            logger.error("some connection DB error");
            ex.printStackTrace();
            return false;
        }
    }

    public List<Table> getAllTables(){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_AVALIABLE_TABLES)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Table> tables = new ArrayList<>();
            while (resultSet.next()) {
                Table tempTable = new Table();
                tempTable.setId(resultSet.getInt("id"));
                tempTable.setSeats(resultSet.getInt("seats"));
                logger.info("table id: " + tempTable.getId());
                logger.info("seats: " + tempTable.getSeats());
                tables.add(tempTable);
            }
            return tables;
        } catch (SQLException ex) {
            logger.error("statement is not performed");
            ex.printStackTrace();
        }
        return null;
    }

    public Table getTableById(int tableId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TABLE_BY_ID)){
            preparedStatement.setInt(1, tableId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Table table = new Table();
            while (resultSet.next()) {
                table.setId(resultSet.getInt("id"));
                table.setSeats(resultSet.getInt("seats"));
                table.setReserved(resultSet.getBoolean("is_reserved"));
                logger.info("table id: " + table.getId());
                logger.info("seats: " + table.getSeats());
            }
            return table;
        } catch (SQLException ex) {
            logger.error("statement is not performed");
            ex.printStackTrace();
        }
        return null;
    }

    public void setTableStatus(boolean status, int tableId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_TABLE_STATUS)){
            preparedStatement.setBoolean(1, status);
            preparedStatement.setInt(2, tableId);
           logger.info("table " + tableId + "is reserved: " + status);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("some connection DB error");
            ex.printStackTrace();
        }
    }

}
