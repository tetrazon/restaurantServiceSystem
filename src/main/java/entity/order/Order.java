package entity.order;

import entity.enumeration.OrderStatus;
import entity.food.DishesInOrder;
import entity.people.Employee;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private int clientId;
    private long timestamp;
    private Table table;
    private List<DishesInOrder> dishes;
    private double invoice;
    private OrderStatus orderStatus;
    private Employee waiterToService;
    private Employee cookToService;

    public Order() {
        invoice = 0;
        dishes = new ArrayList<>();
    }

    public Order(int id, long timestamp, Table table, List<DishesInOrder> dishes,
                 float invoice, OrderStatus orderStatus, Employee waiterToService, Employee cookToService ){
        this.id = id;
        this.timestamp = timestamp;
        this.table = table;
        this.dishes = dishes;
        this.invoice = invoice;
        this.orderStatus = orderStatus;
        this.waiterToService = waiterToService;
        this.cookToService = cookToService;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDishes(List<DishesInOrder> dishes) {
        this.dishes = dishes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<DishesInOrder> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishesInOrder> dishes) {
        this.dishes = dishes;
    }

    public double getInvoice() {
        return invoice;
    }

    public void setInvoice(double invoice) {
        this.invoice = invoice;
    }

    public String getOrderStatus() {
        return orderStatus.toString();
    }

    public void setOrderStatus(String  orderStatus) {
        this.orderStatus = OrderStatus.valueOf(orderStatus);
    }

    public Employee getCookToService() {
        return cookToService;
    }

    public void setCookToService(Employee cookToService) {
        this.cookToService = cookToService;
    }

    public Employee getWaiterToService() {
        return waiterToService;
    }

    public void setWaiterToService(Employee waiterToService) {
        this.waiterToService = waiterToService;
    }

    public void finishOrder(){
        timestamp = (System.currentTimeMillis());
    }

    public static double calculateResultSum(List<DishesInOrder> dishes){
        float sum = 0;
        for (DishesInOrder dishesInOrder : dishes
             ) {
            sum += dishesInOrder.getQuantity() * dishesInOrder.getDish().getPrice();
        }
        return sum;
    }
}


