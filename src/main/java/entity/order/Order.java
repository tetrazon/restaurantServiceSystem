package entity.order;

import entity.food.DishesInOrder;
import entity.people.Employee;

import java.util.ArrayList;

public class Order {

    private long timestamp;
    private Table table;
    private ArrayList<DishesInOrder> dishes;
    private float invoice;
    private OrderStatus orderStatus;
    private Employee waiterToService;
    private Employee cookToService;

    public Order() {
        invoice = 0;
        dishes = new ArrayList<>();
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

    public ArrayList<DishesInOrder> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishesInOrder> dishes) {
        this.dishes = dishes;
    }

    public float getInvoice() {
        return invoice;
    }

    public void setInvoice(float invoice) {
        this.invoice = invoice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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

    public void calculateResultSum(){
        for (DishesInOrder dishesInOrder : dishes
             ) {
            invoice += dishesInOrder.getQuantity() * dishesInOrder.getDish().getPrice();
        }
    }
}


