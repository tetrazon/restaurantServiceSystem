package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.DTO.DishesInOrderDTO;
import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.service.impl.ClientServiceImpl;
import com.smuniov.restaurantServiceSystem.service.impl.EmployeeServiceImpl;
import com.smuniov.restaurantServiceSystem.service.impl.OrderServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RolesAllowed({"ROLE_CLIENT", "ROLE_MANAGER"})
public class OrdersController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private EmployeeServiceImpl employeeService;


    @GetMapping(value="/client/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public @ResponseBody List getOrders(@PathVariable int id){//@RequestBody ClientDataAccess client ;
        List<Order> orders = orderService.getAllByClient_IdOrderByTimestamp(id);
        List<OrderDTO> orderDTOList = OrderDTO.toDTO(orders);
        return orderDTOList;
    }

    @GetMapping(value="/tables")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public List<Table> getTables(){
        Table table = orderService.findTableById(4);
        table.setReserved(false);
        orderService.changeTableStatus(table);
        return orderService.getAllTables();
    }

    @GetMapping(value="/details/{orderId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public List getOrderDetail(@PathVariable int orderId){
        List<DishesInOrder> dishesInOrderList = orderService.findAllDishesInOrderByOrder_Id(orderId);
        return DishesInOrderDTO.toDTO(dishesInOrderList);
    }

    @PostMapping(value = "/client/{clientId}/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity initOrder(@PathVariable int clientId,
                                    @RequestBody List<DishesInOrderDTO> dishesInOrderDTOList){
        orderService.orderInit(clientService.findById(clientId), dishesInOrderDTOList);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/order/{orderId}/book_table/{tableId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity bookTable(@PathVariable int tableId, @PathVariable int orderId){
        orderService.bookTable(tableId, orderService.getOrderById(orderId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/order/{orderId}/process")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<String> processOrder(@PathVariable int orderId){
        Employee waiter = employeeService.getFree("WAITER");
        Employee cook = employeeService.getFree("COOK");
        orderService.processOrder(orderId, waiter, cook);
        //orderService.bookTable(tableId, orderService.getOrderById(orderId));
        return new ResponseEntity(orderService.getOrderById(orderId).getOrderStatus(), HttpStatus.OK);
    }

    @GetMapping(value = "/order/{orderId}/finish")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<OrderDTO> finishOrder(@PathVariable int orderId){
        orderService.finishOrder(orderService.getOrderById(orderId));
        return new ResponseEntity(new OrderDTO(orderService.getOrderById(orderId)), HttpStatus.OK);
    }
}
