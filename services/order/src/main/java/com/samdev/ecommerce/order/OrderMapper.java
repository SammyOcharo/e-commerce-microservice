package com.samdev.ecommerce.order;

import com.samdev.ecommerce.order.product.PurchaseRequest;
import com.samdev.ecommerce.orderLine.OrderLine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setId(orderRequest.id());
        order.setCustomerId(orderRequest.customerId());
        order.setReference(orderRequest.reference());
        order.setTotalAmount(orderRequest.amount());
        order.setPaymentMethod(orderRequest.paymentMethod());

        return order;
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
