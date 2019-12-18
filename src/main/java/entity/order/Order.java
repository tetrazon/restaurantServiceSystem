package entity.order;

import entity.enumeration.OrderStatus;
import entity.food.DishesInOrder;
import entity.users.Employee;

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
    private Employee waiter;
    private Employee cook;

    public Order() {
        invoice = 0;
        dishes = new ArrayList<>();
    }

    public Order(int id, long timestamp, Table table, List<DishesInOrder> dishes,
                 float invoice, OrderStatus orderStatus, Employee waiter, Employee cookToService ){
        this.id = id;
        this.timestamp = timestamp;
        this.table = table;
        this.dishes = dishes;
        this.invoice = invoice;
        this.orderStatus = orderStatus;
        this.waiter = waiter;
        this.cook = cookToService;
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

    public Employee getCook() {
        return cook;
    }

    public void setCook(Employee cook) {
        this.cook = cook;
    }

    public Employee getWaiter() {
        return waiter;
    }

    public void setWaiter(Employee waiter) {
        this.waiter = waiter;
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


