package com.huyendy.services;

import com.huyendy.model.Order;

import com.huyendy.model.User;
import com.huyendy.request.OrderRequset;


import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequset order, User user) throws Exception;

    public Order updaterOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrder(Long userId) throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
