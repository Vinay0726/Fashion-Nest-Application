package com.fashionnest.Fashion_Nest_Application.service;

import com.fashionnest.Fashion_Nest_Application.exception.OrderException;
import com.fashionnest.Fashion_Nest_Application.model.Address;
import com.fashionnest.Fashion_Nest_Application.model.Order;
import com.fashionnest.Fashion_Nest_Application.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user, Address shippingAddress);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId)throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order cancledOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;


}
