package com.samdev.ecommerce.orderLine;

import com.samdev.ecommerce.order.Order;
import com.samdev.ecommerce.order.OrderLineRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderlineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        OrderLine orderLine = new OrderLine();
        Order order = new Order();
        order.setId(orderLineRequest.orderId());
        orderLine.setId(orderLineRequest.id());
        orderLine.setProductId(orderLineRequest.productId());
        orderLine.setQuantity(orderLineRequest.quantity());
        orderLine.setOrder(order);

        System.out.println(orderLine.getProductId());
        System.out.println(orderLine.getQuantity());

        return orderLine;
    }

    public OrderLineResponse toOrderLineResponse(OrderLineResponse orderLineResponse) {

        return new OrderLineResponse(
                orderLineResponse.id(),
                orderLineResponse.quantity()
        );
    }
}
