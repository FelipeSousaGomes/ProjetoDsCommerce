package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderStatus;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Instant moment;
    private OrderStatus stauts;
    private  ClientDTO client;
    private PaymentDTO payment;


    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO(Long id, Instant moment, OrderStatus stauts, ClientDTO client, PaymentDTO payment) {
        this.id = id;
        this.moment = moment;
        this.stauts = stauts;
        this.client = client;
        this.payment = payment;

    }

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.moment = entity.getMoment();
        this.stauts = entity.getStatus();
        this.client = new ClientDTO(entity.getClient());
        this.payment = (entity.getPayment() == null  ) ? null : new PaymentDTO(entity.getPayment());
        for (OrderItem item: entity.getItems()){
                items.add(new OrderItemDTO(item));
        }
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStauts() {
        return stauts;
    }

    public ClientDTO getClient() {
        return client;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Double getTotsl(){
        double sum = 0.0;
        for (OrderItemDTO item : items){
            sum += item.getSubTotal();
        }
        return sum;
    }
}
