package order;

import food.DishesInOrder;
import people.Waiter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Order {

    private Timestamp timestamp;
    private int table;
    private ArrayList<DishesInOrder> dishes;
    private float invoice;
    private OrderStatus orderStatus;
    private Waiter waiterToService;

    public Order() {
        invoice = 0;
        dishes = new ArrayList<>();
    }
    public void finishOrder(){
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void calculateResultSum(){
        for (DishesInOrder dishesInOrder : dishes
             ) {
            invoice += dishesInOrder.getQuantity() * dishesInOrder.getDish().getPrice();
        }
    }
}


