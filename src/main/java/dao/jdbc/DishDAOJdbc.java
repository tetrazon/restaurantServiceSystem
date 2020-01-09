package dao.jdbc;

import dao.DishDAO;
import dao.dataSourse.DataSource;
import entity.food.Dish;
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

public class DishDAOJdbc implements DishDAO {

    private static Logger logger = LoggerFactory.getLogger(Dish.class);
    private final String GET_ALL_DISHES = "SELECT * FROM dishes;";
    private final String GET_DISH_BY_ID = "SELECT * FROM dishes where id = ?;";
    private final  String INSERT_ORDER = "insert into dishes (name, price, description, food_category) VALUES (?,?,?,?);";
    private final String  UPDATE_DISH_BY_ID = "update dishes set price = ?, description = ? where id = ?";
    private final String DELETE_DISH_BY_ID = "delete from dishes where id = ?";

    public void deleteDishById(int dishId){
        try (Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DISH_BY_ID)){
            preparedStatement.setInt(1, dishId);
            preparedStatement.executeUpdate();
            logger.info("dish with id: "+ dishId + " is deleted");
        } catch (SQLException ex) {
            logger.error("dish is not deleted, something goes wrong");
            ex.printStackTrace();
        }
    }

    public void updateDish(Dish dish){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DISH_BY_ID)){
            preparedStatement.setDouble(1, dish.getPrice());
            preparedStatement.setString(2, dish.getDescription());
            preparedStatement.setInt(3, dish.getId());
            preparedStatement.executeUpdate();
            logger.info("dish with id: "+ dish.getId() + " is updated, new price: " + dish.getPrice() + "$");
        } catch (SQLException ex) {
            logger.error("dish is not edited, something goes wrong");
            ex.printStackTrace();
        }
    }

    public void create(Dish dish){
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER)){
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setDouble(2, dish.getPrice());
            preparedStatement.setString(3, dish.getDescription());
            preparedStatement.setString(4, dish.getFoodCategory());
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
            List<Dish> dishes = new ArrayList<>();
            while (resultSet.next()) {
                Dish tempDish = new Dish();
                tempDish.setId(resultSet.getInt("id"));
                tempDish.setName(resultSet.getString("name"));
                tempDish.setPrice(resultSet.getDouble("price"));
                tempDish.setDescription(resultSet.getString("description"));
                tempDish.setFoodCategory(resultSet.getString("food_category"));
                logger.info("dish id: " + tempDish.getId());
                logger.info("dish cat: " + tempDish.getFoodCategory());
                dishes.add(tempDish);
            }
            Collections.sort(dishes, Comparator.comparing(Dish::getId));
            return dishes;
        } catch (SQLException ex) {
            logger.error("statement is not performed");
            ex.printStackTrace();
        }
        return null;
    }

    public Dish getDishById(int dishId){

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DISH_BY_ID)){
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
