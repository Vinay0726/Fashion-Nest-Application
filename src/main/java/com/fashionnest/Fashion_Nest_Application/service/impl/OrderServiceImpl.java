package com.fashionnest.Fashion_Nest_Application.service.impl;

import com.fashionnest.Fashion_Nest_Application.exception.OrderException;
import com.fashionnest.Fashion_Nest_Application.model.Address;
import com.fashionnest.Fashion_Nest_Application.model.Order;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.repository.CartRepository;
import com.fashionnest.Fashion_Nest_Application.service.CartService;
import com.fashionnest.Fashion_Nest_Application.service.OrderService;
import com.fashionnest.Fashion_Nest_Application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartItemService;
    @Autowired
    private ProductService productService;


    @Override
    public Order createOrder(User user, Address shippingAddress) {
        return null;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return List.of();
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

    }
}
