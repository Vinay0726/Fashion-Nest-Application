package com.fashionnest.Fashion_Nest_Application.service.impl;

import com.fashionnest.Fashion_Nest_Application.exception.OrderException;
import com.fashionnest.Fashion_Nest_Application.model.*;
import com.fashionnest.Fashion_Nest_Application.repository.*;
import com.fashionnest.Fashion_Nest_Application.service.CartService;
import com.fashionnest.Fashion_Nest_Application.service.OrderItemService;
import com.fashionnest.Fashion_Nest_Application.service.OrderService;
import com.fashionnest.Fashion_Nest_Application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemRepository orderItemRepository;


    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address= addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart=cartService.findUserCart(user.getId());
        List<OrderItem> orderItems=new ArrayList<>();

        for(CartItem item: cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

            Order createdOrder=new Order();
            createdOrder.setUser(user);
            createdOrder.setOrderItems(orderItems);
            createdOrder.setTotalPrice(cart.getTotalPrice());
            createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
            createdOrder.setDiscount(cart.getDiscount());
            createdOrder.setTotalItem(cart.getTotalItem());
            createdOrder.setShippingAddress(address);
            createdOrder.setOrderDate(LocalDateTime.now());
            createdOrder.setOrderStatus("PENDING");
            createdOrder.getPaymentDetails().setStatus("PENDING");
            createdOrder.setCreatedAt(LocalDateTime.now());

            Order savedOrder=orderRepository.save(createdOrder);

            for(OrderItem item:orderItems) {
                item.setOrder(savedOrder);
                orderItemRepository.save(item);

            }

            return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt=orderRepository.findById(orderId);
        if(opt.isPresent()){
            return  opt.get();
        }
        throw  new OrderException("order not exist with id:"+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {

        List<Order> orders=orderRepository.getUsersOrders(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {

        Order order=findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return orderRepository.save(order);
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
   Order order=findOrderById(orderId);
   orderRepository.deleteById(orderId);
    }
}
