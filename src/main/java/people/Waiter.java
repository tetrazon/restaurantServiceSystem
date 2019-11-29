package people;

import order.Order;

public class Waiter extends Person implements ServeClient {
    private Position position;
    private CurrentStatus currentStatus;

    @Override
    public void serveClient(Client client) {

    }
}
