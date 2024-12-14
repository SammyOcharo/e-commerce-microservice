package com.samdev.ecommerce.orderLine;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderLinesService {

    private final OrderLineRepository orderLineRepository;
    private final OrderlineMapper mapper;

    public OrderLinesService(OrderLineRepository orderLineRepository, OrderlineMapper mapper) {
        this.orderLineRepository = orderLineRepository;
        this.mapper = mapper;
    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {

        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
