package com.samdev.ecommerce.order;

import com.samdev.ecommerce.customer.CustomerClient;
import com.samdev.ecommerce.exception.BusinessException;
import com.samdev.ecommerce.kafka.OrderConfirmation;
import com.samdev.ecommerce.kafka.OrderProducer;
import com.samdev.ecommerce.order.product.PurchaseRequest;
import com.samdev.ecommerce.orderLine.OrderLine;
import com.samdev.ecommerce.orderLine.OrderLineService;
import com.samdev.ecommerce.payment.PaymentClient;
import com.samdev.ecommerce.payment.PaymentRequest;
import com.samdev.ecommerce.product.ProductClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public OrderService(CustomerClient customerClient, ProductClient productClient, OrderRepository orderRepository, OrderMapper mapper, OrderLineService orderLineService, OrderProducer orderProducer, PaymentClient paymentClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
        this.paymentClient = paymentClient;
    }

    public Integer createOrder(OrderRequest orderRequest) throws IOException {

        System.out.println(new ArrayList<>(orderRequest.products()));
        //check the customer ---> openFeign
        var customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(()-> new BusinessException("Customer not found"));

        log.info("We found the customer....");
        log.info("This is the customer firstName: {}", customer.firstname());
        log.info("This is the customer lastName: {}", customer.lastname());

        //purchase the product;
//        try {
            log.info("Purchasing products with product client...");

            var purchasedProducts = productClient.purchaseProducts(orderRequest.products());

            log.info("This are the purchased products: {}", purchasedProducts);
//        } catch (Exception e) {
//            log.error("An error occurred while purchasing products", e);
//            throw new BusinessException("An error occurred while purchasing products");
//        }

        //persists order
        var order = orderRepository.save(mapper.toOrder(orderRequest));


        //persist orderlines
        for(PurchaseRequest purchaseRequest: orderRequest.products()){
            System.out.println("Order ID: " + order.getId() + " Product ID: " + purchaseRequest.productId() + ", Quantity: " + purchaseRequest.quantity());
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }


        //todo start payment process
        paymentClient.requestOrderPayment(new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                orderRequest.reference(),
                customer
        ));

        log.info(String.valueOf(orderRequest.reference()));
        log.info(String.valueOf(orderRequest.amount()));
        log.info(String.valueOf(orderRequest.paymentMethod()));
        log.info(String.valueOf(customer));
        log.info(String.valueOf(purchasedProducts));
        //send the order confirmation --> Notification-ms kafka
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts

                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return  orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()-> new EntityNotFoundException("No order found with given ID"));
    }
}
