package com.samdev.ecommerce.orderLine;

import com.samdev.ecommerce.order.OrderLineRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;

    private final OrderlineMapper mapper;

    public OrderLineService(OrderLineRepository orderLineRepository, OrderlineMapper mapper) {
        this.orderLineRepository = orderLineRepository;
        this.mapper = mapper;
    }

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var order = mapper.toOrderLine(orderLineRequest);

        return orderLineRepository.save(order).getId();
    }
}
