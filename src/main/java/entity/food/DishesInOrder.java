package entity.food;

public class DishesInOrder {
    private  int orderId;
    private Dish dish;
    int quantity;

    public DishesInOrder(){}

    public DishesInOrder(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Dish getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
