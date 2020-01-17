package service;

import dao.DishDAO;
import dao.EmployeeDAO;
import dao.OrderDAO;
import dao.hibernate.DishDAOHibernate;
import dao.hibernate.EmployeeDAOHibernate;
import dao.hibernate.OrderDAOHibernate;
import entity.food.Dish;
import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import entity.users.Client;
import entity.users.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import utils.RoundDouble;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static OrderDAO orderDAO = new OrderDAOHibernate();
    private static DishDAO dishDAO = new DishDAOHibernate();
    private static EmployeeDAO employeeDAO = new EmployeeDAOHibernate();

    public List<Order> getAllOrders(Client client){
       List<Order> orders = orderDAO.getAllOrders(client);
        Collections.sort(orders, (Order o1, Order o2) ->
                o2.getTimestamp() - o1.getTimestamp()>0? 1:o2.getTimestamp() - o1.getTimestamp() == 0? 0:-1);
        return orders;
    }

    public List<Dish> getAllDishes(){
        List<Dish> dishes = dishDAO.getAllDishes();
        Collections.sort(dishes, Comparator.comparing(Dish::getId));
        return dishes;
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

    public void addDishesInOrderItems(int orderId, int[] dishesId, int [] dishQuantities){//!!! -> Order
        List<Dish> dishes = new LinkedList<>();
        for (int i = 0; i < dishesId.length; i++) {
            Dish tempDish = getDishById(dishesId[i]);
            dishes.add(tempDish);
        }
        for (int i = 0; i < dishesId.length; i++) {
            if(dishQuantities[i] == 0){// ignore empty fields
                continue;
            }
            //Order orderWhichDishesAdded = new Order(orderId);
            Order orderWhichDishesAdded = orderDAO.getById(orderId);
            DishesInOrder tempDishesInOrder = new DishesInOrder(orderWhichDishesAdded);
            tempDishesInOrder.setQuantity(dishQuantities[i]);
            tempDishesInOrder.setDish(dishes.get(i));
            //tempDishesInOrder.setOrderId(orderId);//!!! -> Order
            addItemDishesInOrder(tempDishesInOrder);
        }
    }

    public void changeOrderStatus(int orderId, String status){
        Order order = orderDAO.getById(orderId);
        order.setOrderStatus(status);
        logger.info("order status: " + order.getOrderStatus());
        orderDAO.changeOrderStatus(order);
    }

    public Employee callEmployee(String position){
        Employee freeEmployee = employeeDAO.getFreeEmployee(position);
        if (freeEmployee == null){
            logger.info("no free " + position);
            return null;
        }
        if(!changeLoadFactor(freeEmployee, 1)){
            logger.error("critical error, can't change load factor");
            throw new IllegalArgumentException();
        }

        return freeEmployee;
    }

    public boolean changeLoadFactor(Employee employee, int load){  //load = 1 or -1; add listeners for free employees?
        if (load == -1){
            logger.info("some employee is ready to serve!");
        }
        int newLoad = employee.getLoadFactor()+load;
        try {
            if (!(Math.abs(load) < 2)) {

                throw new IllegalArgumentException();
            } else if (!(newLoad < 5 && newLoad >= 0)) {

                throw new IllegalArgumentException();
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        employee.setLoadFactor(newLoad);
        return employeeDAO.updateEmployee(employee);
    }

    public boolean addEmployeesInOrder(Employee waiter, Employee cook, int orderId){
        orderDAO.addEmployeesInOrder(waiter, cook, orderId);

        return true;
    }

    public void setTableStatus(boolean status, int tableId){
        Table table = getTableById(tableId);
        table.setReserved(status);
        orderDAO.setTableStatus(table);
    }

    public Table getTableById(int tableId){
       return orderDAO.getTableById(tableId);
    }

    public boolean initOrder(Client client, int orderedTableId, double invoice){
        Order order = new Order();
        order.setClient(client);
        Table table = orderDAO.getTableById(orderedTableId);
        order.setTable(table);
        order.setInvoice(invoice);
        order.setClient(client);
        order.setOrderStatus("NEW");
        logger.info("order info: table - " + order.getTable().getId() +
                "; invoice: " + order.getInvoice() + "; client id: " + order.getClient().getId());
        return orderDAO.initOrder(order);
    }

    public void setTimeOfOrder(int orderId){
        Order order = orderDAO.getById(orderId);
        order.setTimestamp(System.currentTimeMillis());
        logger.info("updated order time: " + order.getTimestamp());
        orderDAO.setTimeOfOrder(order);
    }

    public Integer getOrderId(Client client, String orderStatus){
        Integer orderId = orderDAO.getOrderId(client, orderStatus);
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
            logger.info("quantity[i]: " + quantity[i] + "; dishPrices[i]: " + dishPrices[i]);
            invoice += quantity[i]*dishPrices[i];
        }
        logger.info("invoice before round: " + invoice);
        return RoundDouble.roundDouble(invoice, 2);
    }

    public double calculateSumOfOrder(List<DishesInOrder> dishesInOrderList){
        double sum = 0;
        for (int i = 0; i < dishesInOrderList.size(); i++) {
            sum += dishesInOrderList.get(i).getQuantity()*dishesInOrderList.get(i).getDish().getPrice();
        }
        logger.info("invoice from order History: " + sum);
        return RoundDouble.roundDouble(sum, 2);
    }

    public String processOrder( int orderId, int tableId,
                               double invoice, int[] dishesId, int [] dishQuantities){
        String response = "";
       // Order order = new Order();
        Employee waiter = callEmployee("WAITER");
        logger.info("waiter load factor: " + waiter.getLoadFactor());
        Employee cook = callEmployee("COOK");
        logger.info("cook load factor: " + cook.getLoadFactor());
        if(cook != null && waiter != null){
            addEmployeesInOrder(waiter, cook, orderId);
            try {
                changeOrderStatus(orderId, "IS_PROCESSING");
                waiter.processOrder();
                cook.processOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishingOrder(waiter,cook, orderId, tableId);
            addDishesInOrderItems(orderId, dishesId, dishQuantities);//!!! -> Order
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
        logger.info("waiter load factor before finishing order: " + waiter.getLoadFactor());
        changeLoadFactor(waiter, -1);
        logger.info("waiter load factor after finishing order: " + waiter.getLoadFactor());
        changeLoadFactor(cook, -1);
        changeOrderStatus(orderId, "IS_FINISHED");
        setTableStatus(false, tableId);

        setTimeOfOrder(orderId);
    }

    public List<DishesInOrder> getDishesFromOrder(int orderId){
        Order order = orderDAO.getById(orderId);
        return orderDAO.getDishesFromOrder(order);
    }

    public void updateDishById(int dishId, double newPrice, String newDescription){
        Dish dish = dishDAO.getDishById(dishId);
        dish.setPrice(newPrice);
        dish.setDescription(newDescription);
        dishDAO.updateDish(dish);
    }

    public void deleteDishById(int dishId){
        dishDAO.deleteDishById(dishId);
    }

    public void addDish(Dish dish){
        dishDAO.create(dish);
    }



}
