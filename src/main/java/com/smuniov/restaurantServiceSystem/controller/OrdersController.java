package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.DTO.DishesInOrderDTO;
import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.assembler.OrderDTOAssembler;
import com.smuniov.restaurantServiceSystem.entity.food.DishesInOrder;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.service.ClientServiceI;
import com.smuniov.restaurantServiceSystem.service.EmployeeServiceI;
import com.smuniov.restaurantServiceSystem.service.OrderServiceI;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
@RolesAllowed({"ROLE_CLIENT"})
public class OrdersController {

    private final OrderServiceI orderService;
    private final ClientServiceI clientService;
    private final EmployeeServiceI employeeService;
    private final OrderDTOAssembler orderDTOAssembler;
    public OrdersController(OrderServiceI orderService, ClientServiceI clientService,
                            EmployeeServiceI employeeService, OrderDTOAssembler orderDTOAssembler) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.orderDTOAssembler = orderDTOAssembler;
    }


    @RolesAllowed({"ROLE_MANAGER"})
    @GetMapping(value="/client/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity getOrders(@PathVariable int id, Pageable pageable){
        return getResponseEntity(id, pageable);
    }

    private ResponseEntity getResponseEntity(@PathVariable int id, Pageable pageable) {
        List<Order> orders = orderService.getAllByClient_IdOrderByTimestamp(id);
        List<OrderDTO> orderDTOList = OrderDTO.toDTO(orders);
        Page<OrderDTO> ordersDTOPage= new PageImpl<>(orderDTOList, pageable, orderDTOList.size());
        return new ResponseEntity(ordersDTOPage, HttpStatus.FOUND);
    }

    @GetMapping(value="/history")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<PagedModel<OrderDTO>> getOrders(@PageableDefault(page=0, size = 10, sort = {"timestamp"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                          PagedResourcesAssembler<Order> pagedResourcesAssembler){
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int clientId = clientService.getClientByEmail(userDetails.getUsername()).getId();
        Page<Order> orders = orderService.findAllOrdersByClientId(pageable, clientId);
        PagedModel<OrderDTO> pagedModel = pagedResourcesAssembler.toModel(orders, orderDTOAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.FOUND);
    }

    @GetMapping(value="/history/{orderId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public List getOrderDetail(@PathVariable int orderId){
        List<DishesInOrder> dishesInOrderList = orderService.findAllDishesInOrderByOrder_Id(orderId);
        return DishesInOrderDTO.toDTO(dishesInOrderList);
    }

    @PostMapping(value = "/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity initOrder(@RequestBody OrderDTO orderDTO){
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int clientId = clientService.getClientByEmail(userDetails.getUsername()).getId();
        OrderDTO orderDTOresp = orderService.orderInit(clientService.findById(clientId), orderDTO);
        return new ResponseEntity(orderDTOresp, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{orderId}/process")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<String> processOrder(@PathVariable int orderId){
        Employee waiter = employeeService.getFree("WAITER");
        Employee cook = employeeService.getFree("COOK");
        orderService.processOrder(orderId, waiter, cook);
        return new ResponseEntity(orderService.getOrderById(orderId).getOrderStatus(), HttpStatus.OK);
    }

    @GetMapping(value = "/{orderId}/finish")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<OrderDTO> finishOrder(@PathVariable int orderId){
        orderService.finishOrder(orderService.getOrderById(orderId));
        OrderDTO orderDTO = new OrderDTO(orderService.getOrderById(orderId));
        orderDTO.add(linkTo(
                methodOn(OrdersController.class)
                        .getOrders(null, null))
                .slash(orderDTO.getId())
                .withSelfRel());
        return new ResponseEntity(orderDTO, HttpStatus.OK);
    }
}
