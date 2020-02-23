package com.smuniov.restaurantServiceSystem.assembler;

import com.smuniov.restaurantServiceSystem.DTO.OrderDTO;
import com.smuniov.restaurantServiceSystem.controller.OrdersController;
import com.smuniov.restaurantServiceSystem.entity.order.Order;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                        .getOrderDetail(orderDTO.getId()))
                        //.getOrders(null))
                        //.slash(orderDTO.getId())
                        .withSelfRel()
                        .withType("GET"));
        orderDTO.add(linkTo((methodOn(OrdersController.class)
                //.slash("/history")
                .getOrders(null)))
                .withRel("all").withType("GET"));
        return orderDTO;
    }

    @Override
    public CollectionModel<OrderDTO> toCollectionModel(Iterable<? extends Order> entities) {
        CollectionModel<OrderDTO> dtoCollectionModel = super.toCollectionModel(entities);
        dtoCollectionModel.add(linkTo(
                methodOn(OrdersController.class)
                .getOrders(null))
                .withSelfRel().withType("GET"));
//        dtoCollectionModel.add(linkTo(
//                methodOn(OrdersController.class))
//                .slash("/history")
//                .withRel("all").withType("GET"));
        return dtoCollectionModel;
    }
}
