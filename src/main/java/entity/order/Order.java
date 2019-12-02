package entity.order;

import entity.food.DishesInOrder;
import people.Employee;

import java.util.ArrayList;

public class Order {

    private long timestamp;
    private Table table;
    private ArrayList<DishesInOrder> dishes;
    private float invoice;
    private OrderStatus orderStatus;
    private Employee employeeToService;

    public Order() {
        invoice = 0;
        dishes = new ArrayList<>();
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


