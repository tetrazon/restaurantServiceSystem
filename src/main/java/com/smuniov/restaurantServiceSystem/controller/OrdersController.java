package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.DTO.DishesInOrderDTO;
import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RolesAllowed({"ROLE_CLIENT", "ROLE_MANAGER"})
public class OrdersController {

@Autowired
private OrderServiceImpl orderService;


    @GetMapping(value="/client/{id}")
    public @ResponseBody List getOrders(@PathVariable int id){//@RequestBody ClientDataAccess client ;
        List<Order> orders = orderService.getAllByClient_IdOrderByTimestamp(id);
        List<OrderDTO> orderDTOList = OrderDTO.toDTO(orders);
        return orderDTOList;
    }

    @GetMapping(value="/tables")
    public List<Table> getTables(){
        return orderService.getAllTables();
                //orderService.getAllTables();
    }

    @GetMapping(value="/details/{orderId}")
    public List getOrderDetail(@PathVariable int orderId){
        List<DishesInOrder> dishesInOrderList = orderService.findAllDishesInOrderByOrder_Id(orderId);
        return DishesInOrderDTO.toDTO(dishesInOrderList);
    }

}
