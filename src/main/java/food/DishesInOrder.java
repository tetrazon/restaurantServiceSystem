package food;

public class DishesInOrder {

    private Dish dish;
    int quantity;

    public DishesInOrder(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
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

    private void setDish(Dish dish) {
        this.dish = dish;
    }
}
