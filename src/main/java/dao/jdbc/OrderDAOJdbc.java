package dao.jdbc;

import dao.OrderDAO;
import dao.dataSourse.DataSource;
import entity.food.Dish;
import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import entity.users.Client;
import entity.users.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDAOJdbc implements OrderDAO {//check statements!

    private static Logger logger = LoggerFactory.getLogger(OrderDAOJdbc.class);
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
    private final String GET_ALL_DISHES_IN_ORDER = "select dishes.name, dishes.price, dishes_in_order.quantity from dishes_in_order " +
            "join dishes ON dishes.id = dishes_in_order.dish_id_fk WHERE dishes_in_order.order_id_fk = ?;";

public void insertDishesInOrder(DishesInOrder dishesInOrder){
    try (Connection connection = DataSource.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DISHES_IN_ORDER)){
        preparedStatement.setInt(1, dishesInOrder.getDish().getId());
        preparedStatement.setInt(2, dishesInOrder.getOrder().getId());
        preparedStatement.setInt(3, dishesInOrder.getQuantity());
        preparedStatement.executeUpdate();
        logger.info("dishes in order is added");
    } catch (SQLException ex) {
        logger.info("dishes in order are not added, db connection goes wrong");
        ex.printStackTrace();
    }
}

    public void createOrder(Order order){//client
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER)){
            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setString(2, order.getOrderStatus());
            preparedStatement.setInt(3, order.getWaiter().getId());
            preparedStatement.setInt(4, order.getCook().getId());
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

    public void setTimeOfOrder(Order order){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ORDER_TIME)){
            preparedStatement.setLong(1, order.getTimestamp());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.executeUpdate();
            logger.info("order time is set: " + order.getTimestamp());
            logger.info("order id: " + order.getId());
        } catch (SQLException ex) {
            logger.info("order time is not set, something goes wrong");
            ex.printStackTrace();
        }
    }

    public boolean initOrder(Order order){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INIT_ORDER)){
            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setInt(2, order.getTable().getId());
            preparedStatement.setString(3, "NEW");
            preparedStatement.setDouble(4, order.getInvoice());
            preparedStatement.executeUpdate();
            logger.info("order is initialized, order status is NEW");
            return true;
        } catch (SQLException ex) {
            logger.error("order is not added, something goes wrong");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean changeOrderStatus(Order order){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_ORDER_STATUS)){
            preparedStatement.setString(1, order.getOrderStatus());//stat
            preparedStatement.setInt(2, order.getId());//id
            preparedStatement.executeUpdate();
            logger.info("order is changed, order status is" + order.getOrderStatus());
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

    public Integer getOrderId (Client client, String orderStatus){//client
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_ID)){
            preparedStatement.setInt(1, client.getId());
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

    public List<Order> getAllOrders(Client client){//client

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS)){
            preparedStatement.setInt(1, client.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order tempOrder = new Order();
                tempOrder.setId(resultSet.getInt("id"));
                tempOrder.setTimestamp(resultSet.getLong("date_of_order"));
                tempOrder.setInvoice(resultSet.getDouble("invoice"));
                tempOrder.setOrderStatus(resultSet.getString("order_status"));
                logger.info("order id: " + tempOrder.getId());
                orders.add(tempOrder);
            }
            Collections.sort(orders, (Order o1, Order o2) ->
                    o2.getTimestamp() - o1.getTimestamp()>0? 1:o2.getTimestamp() - o1.getTimestamp() == 0? 0:-1);
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean addEmployeesInOrder(Employee waiter, Employee cook, int orderId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_EMPL_IN_ORDER)){
            preparedStatement.setInt(1, waiter.getId());
            preparedStatement.setInt(2, cook.getId());
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

    public void setTableStatus(Table table){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_TABLE_STATUS)){
            preparedStatement.setBoolean(1, table.getIsReserved());
            preparedStatement.setInt(2, table.getId());
           logger.info("table " + table.getId() + "is reserved: " + table.getIsReserved());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("some connection DB error");
            ex.printStackTrace();
        }
    }

    public List<DishesInOrder> getDishesFromOrder(Order order){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DISHES_IN_ORDER)){
            preparedStatement.setInt(1, order.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DishesInOrder> dishesInOrder = new ArrayList<>();
            while (resultSet.next()) {
                Dish tempDish = new Dish();
                DishesInOrder tempDishesInOrder = new DishesInOrder();
                tempDish.setName(resultSet.getString("name"));
                tempDish.setPrice(resultSet.getDouble("price"));
                tempDishesInOrder.setQuantity(resultSet.getInt("quantity"));
                tempDishesInOrder.setDish(tempDish);
                logger.info("dish name: " + tempDish.getName());
                logger.info("dish $: " + tempDish.getPrice());
                logger.info("quantity: " + tempDishesInOrder.getQuantity());
                dishesInOrder.add(tempDishesInOrder);
            }
            return dishesInOrder;
        } catch (SQLException ex) {
            logger.error("statement is not performed");
            ex.printStackTrace();
        }
        return null;
    }

}
