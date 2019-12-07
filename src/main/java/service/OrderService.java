package service;

import dao.OrderDAO;
import entity.order.Order;

public class OrderService {
    //create, update, remove, getAll, get(id)
    private static OrderDAO orderDAO = new OrderDAO();

    public void create(Order order){

    }

    public void getAll(int clientId){
        orderDAO.getAll(clientId);

    }
}
