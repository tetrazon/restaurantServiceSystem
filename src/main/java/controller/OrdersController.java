package controller;

import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.hardCode.ClientService;
import service.hardCode.OrderService;

import java.util.List;

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

    @GetMapping(value="/client/{id}")
    //@RequestMapping(value="/client/{id}", method= RequestMethod.GET, produces="application/json")// this method will only handle requests where JSON output is expected.
    public @ResponseBody List<Order> getOrders(@PathVariable int id){//@RequestBody Client client ;
        return orderService.getAllOrders(clientService.getClientById(id));
    }

    @GetMapping(value="/tables")
    //@RequestMapping(value="/client/{id}", method= RequestMethod.GET, produces="application/json")// this method will only handle requests where JSON output is expected.
    public @ResponseBody List<Table> getTables(){
        return orderService.getAllTables();
    }

    @GetMapping(value="/details/{orderId}")
    //@RequestMapping(value="/details/{orderId}", method= RequestMethod.GET, produces="application/json")
    public @ResponseBody List<DishesInOrder> getOrderDetail(@PathVariable int orderId){

        return orderService.getDishesFromOrder(orderId);
    }

}
