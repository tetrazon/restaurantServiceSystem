package service;

import dao.DishDAO;
import dao.EmployeeDAO;
import dao.OrderDAO;
import entity.food.Dish;
import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import entity.people.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static OrderDAO orderDAO = new OrderDAO();
    private static DishDAO dishDAO = new DishDAO();
    private static EmployeeDAO employeeDAO = new EmployeeDAO();

    public void create(Order order){

    }

    public List<Order> getAllClients(int clientId){
       return orderDAO.getAllOrders(clientId);
    }

    public List<Dish> getAllDishes(){
        return dishDAO.getAllDishes();
    }

    public Dish getDish(int dishId){
        return dishDAO.getDish(dishId);
    }

    public List<Table> getAllTables(){
        return orderDAO.getAllTables();
    }


    public void addEmployeeInOrder(int orderId, int employeeId){

        //orderDAO.addEmployeesInOrder(freeWaiter.getId(),freeWaiter.getId(), orderId);
    }

    public void addOrder(Order order){
        orderDAO.createOrder(order);
    }

    public void addDishesInOrder(DishesInOrder dishesInOrder){
        orderDAO.insertDishesInOrder(dishesInOrder);
    }

    public void changeOrderStatus(int orderId, String status){
        orderDAO.changeOrderStatus(orderId, status);
    }

    public Employee callEmployee(String position){
        Employee freeEmployee = employeeDAO.getFreeEmployee(position);
        if (freeEmployee == null){
            logger.info("no free " + position);
            return null;
        }
        if(!changeLoadFactor(freeEmployee.getId(), 1)){
            logger.error("critical error, can't change load factor");
            throw new IllegalArgumentException();
        }

        return freeEmployee;
    }

    public boolean changeLoadFactor(int employeeId, int load){  //load = 1 or -1; add listeners for free employees?
        if (load == -1){
            logger.info("some employee is ready to serve!");
        }
        return employeeDAO.changeLoadFactor(load, employeeId);
    }

    public boolean addEmployeesInOrder(int waiterId, int cookId, int orderId){
        orderDAO.addEmployeesInOrder(waiterId, cookId, orderId);

        return true;
    }

    public void setTableStatus(boolean status, int tableId){
        orderDAO.setTableStatus(status, tableId);
    }

    public Table getTableById(int tableId){
       return orderDAO.getTableById(tableId);
    }

    public boolean initOrder(int clientId, int orderedTableId, double invoice){
        return orderDAO.initOrder(clientId, orderedTableId, invoice);
    }

    public void setTimeOfOrder(int orderId){
        orderDAO.setTimeOfOrder(orderId);
    }

    public Integer getOrderId(int clientId, String orderStatus){
        Integer orderId = orderDAO.getOrderId(clientId, orderStatus);
        if (orderId == null){
            logger.error("error getting order id");
            throw new NullPointerException();
        }
        return orderId;
    }

     synchronized public int[] stringToIntArray(String[] strings){
        int [] ints = new int[strings.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
            logger.info("iteration: " + i + "; int[i]=" + ints[i]);
        }
        return ints;
    }

    public double[] stringToDoubleArray(String[] strings){
        double [] doubles = new double[strings.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = Double.parseDouble(strings[i]);
        }
        return doubles;
    }

    public double calculateSumOfOrder(int[] quantity, double [] dishPrices){
        double invoice = 0;
        for (int i = 0; i < quantity.length; i++) {
            invoice += quantity[i]*dishPrices[i];
        }
        return invoice;
    }


}
