package com.smuniov.restaurantServiceSystem.DTO;

import com.smuniov.restaurantServiceSystem.entity.order.Order;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
public class OrderDTO extends RepresentationModel<OrderDTO>{
    private int id;
    private Long timestamp;
    private Double invoice;
    private String orderStatus;
    private int tableId;

    private List<DishesInOrderDTO> dishesInOrderDTOS = new ArrayList<>();

    public OrderDTO(){
    }

    public OrderDTO(Order order){
        id = order.getId();
        timestamp = order.getTimestamp();
        invoice = order.getInvoice();
        orderStatus = order.getOrderStatus();
        tableId = order.getTable().getId();
    }

    public static List<OrderDTO> toDTO(List<Order> orders){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            orderDTOList.add(new OrderDTO(orders.get(i)));
        }
        return orderDTOList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getInvoice() {
        return invoice;
    }

    public void setInvoice(Double invoice) {
        this.invoice = invoice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public List<DishesInOrderDTO> getDishesInOrderDTOS() {
        return dishesInOrderDTOS;
    }

    public void setDishesInOrderDTOS(List<DishesInOrderDTO> dishesInOrderDTOS) {
        this.dishesInOrderDTOS = dishesInOrderDTOS;
    }
}
