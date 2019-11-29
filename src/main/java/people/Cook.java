package people;

import order.Order;

public class Cook extends Person implements ServeClient{

    private CurrentStatus currentStatus;
    private Position position;

    @Override
    public void serveClient(Client client) {

    }
}
