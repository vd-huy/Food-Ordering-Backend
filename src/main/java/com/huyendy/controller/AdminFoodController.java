package com.huyendy.controller;

import com.huyendy.model.Food;
import com.huyendy.model.Restaurant;
import com.huyendy.model.User;
import com.huyendy.request.CreateFoodRequest;
import com.huyendy.respones.MessageRespone;
import com.huyendy.services.FoodService;
import com.huyendy.services.RestaurantService;
import com.huyendy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req,req.getFoodCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageRespone> deleteFood(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);

        MessageRespone messageRespone = new MessageRespone();
        messageRespone.setMessage("Food deleted successfully");

        return new ResponseEntity<>(messageRespone, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Food food= foodService.updateAvailibilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }



}
