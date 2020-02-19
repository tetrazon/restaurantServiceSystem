package com.smuniov.restaurantServiceSystem.assembler;

import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.controller.OrdersController;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDTOAssembler extends RepresentationModelAssemblerSupport<Order, OrderDTO> {
    public OrderDTOAssembler() {
        super(OrdersController.class, OrderDTO.class);
    }

    @Override
    public OrderDTO toModel(Order entity) {
        OrderDTO orderDTO = new OrderDTO(entity);
        orderDTO.add(linkTo(
                methodOn(OrdersController.class)
                        .getOrders(null, null))
                        .slash(orderDTO.getId())
                        .withSelfRel());
        orderDTO.add(linkTo(OrdersController.class)
                .slash("/orders/history?page={?}&size={?}&sort={timestamp,invoice,orderStatus,tableId},{asc,desc}")
                .withRel("all"));
        return orderDTO;
    }

    @Override
    public CollectionModel<OrderDTO> toCollectionModel(Iterable<? extends Order> entities) {
        CollectionModel<OrderDTO> dtoCollectionModel = super.toCollectionModel(entities);
        dtoCollectionModel.add(linkTo(
                methodOn(OrdersController.class)
                .getOrders(null, null))
                .withSelfRel());
        return dtoCollectionModel;
    }
}
