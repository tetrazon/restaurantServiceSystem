package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.DTO.DishesInOrderDTO;
import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.service.impl.ClientServiceImpl;
import com.smuniov.restaurantServiceSystem.service.impl.EmployeeServiceImpl;
import com.smuniov.restaurantServiceSystem.service.impl.OrderServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RolesAllowed({"ROLE_CLIENT", "ROLE_MANAGER"})
public class OrdersController {

    private final OrderServiceImpl orderService;

    private final ClientServiceImpl clientService;

    private final EmployeeServiceImpl employeeService;

    public OrdersController(OrderServiceImpl orderService, ClientServiceImpl clientService, EmployeeServiceImpl employeeService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.employeeService = employeeService;
    }


    @GetMapping(value="/client/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity getOrders(@PathVariable int id, Pageable pageable){//@RequestBody ClientDataAccess client ; @ResponseBody List
        List<Order> orders = orderService.getAllByClient_IdOrderByTimestamp(id);
        List<OrderDTO> orderDTOList = OrderDTO.toDTO(orders);
        Page<OrderDTO> ordersDTOPage= new PageImpl<>(orderDTOList, pageable, orderDTOList.size());
        return new ResponseEntity(ordersDTOPage, HttpStatus.FOUND);
    }


    @GetMapping(value="/details/{orderId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public List getOrderDetail(@PathVariable int orderId){
        List<DishesInOrder> dishesInOrderList = orderService.findAllDishesInOrderByOrder_Id(orderId);
        return DishesInOrderDTO.toDTO(dishesInOrderList);
    }

    @PostMapping(value = "/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity initOrder(@RequestBody List<DishesInOrderDTO> dishesInOrderDTOList){
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int clientId = clientService.getClientByEmail(userDetails.getUsername()).getId();
        orderService.orderInit(clientService.findById(clientId), dishesInOrderDTOList);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{orderId}/book_table/{tableId}")// change to presetted table
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity bookTable(@PathVariable int tableId, @PathVariable int orderId){
        orderService.bookTable(tableId, orderService.getOrderById(orderId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/{orderId}/process")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<String> processOrder(@PathVariable int orderId){
        Employee waiter = employeeService.getFree("WAITER");
        Employee cook = employeeService.getFree("COOK");
        orderService.processOrder(orderId, waiter, cook);
        //orderService.bookTable(tableId, orderService.getOrderById(orderId));
        return new ResponseEntity(orderService.getOrderById(orderId).getOrderStatus(), HttpStatus.OK);
    }

    @GetMapping(value = "/{orderId}/finish")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<OrderDTO> finishOrder(@PathVariable int orderId){
        orderService.finishOrder(orderService.getOrderById(orderId));
        return new ResponseEntity(new OrderDTO(orderService.getOrderById(orderId)), HttpStatus.OK);
    }
}
