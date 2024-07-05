package com.huyendy.controller;


import com.huyendy.model.CartItem;
import com.huyendy.model.Order;
import com.huyendy.model.User;
import com.huyendy.request.AddCartItemRequest;
import com.huyendy.request.OrderRequset;
import com.huyendy.services.OrderService;
import com.huyendy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequset req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        Order order = orderService.createOrder(req,user);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestBody OrderRequset req,
                                               @RequestHeader("Authorization") String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        List<Order> orders = orderService.getUserOrder(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
