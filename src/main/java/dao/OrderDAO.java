package dao;

import dao.dataSourse.DataSource;
import entity.order.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAO {

    public void create(Order order, int clientId, int waiterId, int cookId){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into orders (client_id_fk, order_status, " +
                     "waiter_id_fk, cook_id_fk, ordered_table_fk, invoice, date_of_order) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?);")){
            preparedStatement.setInt(1, clientId);
            preparedStatement.setString(2, order.getOrderStatus().toString());
            preparedStatement.setInt(3, waiterId);
            preparedStatement.setInt(4, cookId);
            preparedStatement.setInt(5, order.getTable().getId());
            preparedStatement.setDouble(6, order.getInvoice());
            preparedStatement.setLong(7, order.getTimestamp());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Order order){

    }

    public void getAll(){

    }

    public void get(int id){

    }

    public void update(){

    }

}
