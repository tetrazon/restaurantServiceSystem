package spring.controller;

import entity.food.DishesInOrder;
import entity.order.Order;
import entity.users.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.ClientService;
import service.OrderService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private OrderService orderService;
    private ClientService clientService;

    @Autowired
    public OrdersController(OrderService orderService, ClientService clientService){
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @RequestMapping(value="/client/{id}", method= RequestMethod.GET, produces="application/json")// this method will only handle requests where JSON output is expected.
    public @ResponseBody List<Order> getOrders(@PathVariable int id){//@RequestBody Client client ;
        return orderService.getAllOrders(clientService.getClientById(id));
    }

    @RequestMapping(value="/details/{orderId}", method= RequestMethod.GET, produces="application/json")
    public @ResponseBody List<DishesInOrder> getOrderDetail(@PathVariable int orderId){

        return orderService.getDishesFromOrder(orderId);
    }

}
