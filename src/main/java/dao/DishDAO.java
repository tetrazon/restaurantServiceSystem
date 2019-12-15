package dao;

import dao.dataSourse.DataSource;
import entity.food.Dish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishDAO {

    private static Logger logger = LoggerFactory.getLogger(Dish.class);
    private final String GET_ALL_DISHES = "SELECT * FROM dishes;";
    private final String GET_DISH = "SELECT * FROM dishes where id = ?;";
    private final  String INSERT_ORDER = "insert into dishes (name, price, description, food_category) VALUES (?,?,?,?);";



    public void create(Dish order){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER)){
            preparedStatement.setString(1, order.getName());
            preparedStatement.setDouble(2, order.getPrice());
            preparedStatement.setString(3, order.getDescription());
            preparedStatement.setString(4, order.getFoodCategory());
            preparedStatement.executeUpdate();
            logger.info("dish is added");
        } catch (SQLException ex) {
            logger.error("dish is not added, something goes wrong");
            ex.printStackTrace();
        }
    }

    public List<Dish> getAllDishes(){

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DISHES)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Dish> orders = new ArrayList<>();
            while (resultSet.next()) {
                Dish tempDish = new Dish();
                tempDish.setId(resultSet.getInt("id"));
                tempDish.setName(resultSet.getString("name"));
                tempDish.setPrice(resultSet.getDouble("price"));
                tempDish.setDescription(resultSet.getString("description"));
                tempDish.setFoodCategory(resultSet.getString("food_category"));
                logger.info("dish id: " + tempDish.getId());
                logger.info("dish cat: " + tempDish.getFoodCategory());
                orders.add(tempDish);
            }
            return orders;
        } catch (SQLException ex) {
            logger.error("statement is not performed");
            ex.printStackTrace();
        }
        return null;
    }

    public Dish getDish(int dishId){

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DISH)){
            preparedStatement.setInt(1, dishId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Dish dish = new Dish();
            while (resultSet.next()) {
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setPrice(resultSet.getDouble("price"));
                dish.setDescription(resultSet.getString("description"));
                dish.setFoodCategory(resultSet.getString("food_category"));
                logger.info("dish id: " + dish.getId());
                logger.info("dish cat: " + dish.getFoodCategory());
            }
            return dish;
        } catch (SQLException ex) {
            logger.error("statement is not performed");
            ex.printStackTrace();
        }
        return null;
    }



}
