package com.huyendy.controller;

import com.huyendy.model.Order;
import com.huyendy.model.User;
import com.huyendy.request.OrderRequset;
import com.huyendy.services.OrderService;
import com.huyendy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id,
                                                       @RequestParam(required = false) String order_status,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        List<Order> orders = orderService.getRestaurantOrder(id,order_status);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                         @PathVariable String orderStatus,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);

        Order order = orderService.updaterOrder(orderId,orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
