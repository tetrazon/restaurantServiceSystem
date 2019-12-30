package service;

import dao.DishDAO;
import dao.EmployeeDAO;
import dao.OrderDAO;
import entity.food.Dish;
import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import entity.users.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static OrderDAO orderDAO = new OrderDAO();
    private static DishDAO dishDAO = new DishDAO();
    private static EmployeeDAO employeeDAO = new EmployeeDAO();

    public List<Order> getAllClients(int clientId){
       return orderDAO.getAllOrders(clientId);
    }

    public List<Dish> getAllDishes(){
        return dishDAO.getAllDishes();
    }

    public Dish getDishById(int dishId){
        return dishDAO.getDishById(dishId);
    }

    public List<Table> getAllTables(){
        return orderDAO.getAllTables();
    }

    public void addOrder(Order order){
        orderDAO.createOrder(order);
    }

    public void addItemDishesInOrder(DishesInOrder dishesInOrder){
        orderDAO.insertDishesInOrder(dishesInOrder);
    }

    public void addDishesInOrderItems(int orderId, int[] dishesId, int [] dishQuantities){
        List<Dish> dishes = new LinkedList<>();
        for (int i = 0; i < dishesId.length; i++) {
            Dish tempDish = getDishById(dishesId[i]);
            dishes.add(tempDish);
        }
        for (int i = 0; i < dishesId.length; i++) {
            if(dishQuantities[i] == 0){// ignore empty fields
                continue;
            }
            DishesInOrder tempDishesInOrder = new DishesInOrder();
            tempDishesInOrder.setQuantity(dishQuantities[i]);
            tempDishesInOrder.setDish(dishes.get(i));
            tempDishesInOrder.setOrderId(orderId);
            addItemDishesInOrder(tempDishesInOrder);
        }
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
        int emptyCount= 0;
        for (int i = 0; i < ints.length; i++) {
            if(strings[i] == null || strings[i].equals("") ){
                ints[i] = 0;
                emptyCount ++;
                continue;
            }
            ints[i] = Integer.parseInt(strings[i]);
            logger.info("iteration: " + i + "; int[i]=" + ints[i]);
        }
        if(emptyCount == strings.length){
            return null;
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
        return roundDouble(invoice, 2);
    }

    public double calculateSumOfOrder(List<DishesInOrder> dishesInOrderList){
        double sum = 0;
        for (int i = 0; i < dishesInOrderList.size(); i++) {
            sum += dishesInOrderList.get(i).getQuantity()*dishesInOrderList.get(i).getDish().getPrice();
        }
        return roundDouble(sum, 2);
    }

    private double roundDouble(double doubleToRound, int scale){
        if (scale < 0) throw new IllegalArgumentException();
        return new BigDecimal(doubleToRound).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public String processOrder(int clientId, int orderId, int tableId,
                               double invoice, int[] dishesId, int [] dishQuantities){
        String response = "";
       // Order order = new Order();
        Employee waiter = callEmployee("WAITER");
        Employee cook = callEmployee("COOK");
        if(cook != null && waiter != null){
            addEmployeesInOrder(waiter.getId(),cook.getId(), orderId);
            try {
                changeOrderStatus(orderId, "IS_PROCESSING");
                waiter.processOrder();
                cook.processOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishingOrder(waiter,cook, orderId, tableId);
            addDishesInOrderItems(orderId, dishesId, dishQuantities);
            return "IS_FINISHED";
        } else if(cook == null && waiter!= null){
            // add waiter in order, form order, set order status cook_queue, and info need wait COOK, add listeners to catch COOK
        } else if(waiter == null && cook != null){
            // add cook in order, form order, set order status waiter, and info need wait waiter, add listeners to catch waiter
        } else {
            //set set order status ALL_queue
            //  and info need wait WAITER, COOK, add listeners to catch WAITER, COOK
        }
        return "ERROR";
    }

    public void finishingOrder(Employee waiter, Employee cook, int orderId, int tableId){
        changeLoadFactor(waiter.getId(), -1);
        changeLoadFactor(cook.getId(), -1);
        changeOrderStatus(orderId, "IS_FINISHED");
        setTableStatus(false, tableId);
        setTimeOfOrder(orderId);
    }

    public List<DishesInOrder> getDishesFromOrder(int orderId){
        return orderDAO.getDishesFromOrder(orderId);
    }

    public void updateDishById(int dishId, double newPrice, String newDescription){
        dishDAO.updateDishById(dishId, newPrice, newDescription);
    }

    public void deleteDishById(int dishId){
        dishDAO.deleteDishById(dishId);
    }

    public void addDish(Dish dish){
        dishDAO.create(dish);
    }



}
