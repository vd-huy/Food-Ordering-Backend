package com.huyendy.controller;

import com.huyendy.model.Restaurant;
import com.huyendy.model.User;
import com.huyendy.request.CreatedRestaurantRequest;
import com.huyendy.respones.MessageRespone;
import com.huyendy.services.RestaurantService;
import com.huyendy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurant")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(@RequestHeader("Authorization") String jwt, @RequestBody CreatedRestaurantRequest req) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.createdRestaurant(req,user);


        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestHeader("Authorization") String jwt,
                                                       @RequestBody CreatedRestaurantRequest req,
                                                       @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurant(id,req);


        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageRespone> deleteRestaurant(@RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        restaurantService.deleteRestaurant(id);

        MessageRespone messageRespone = new MessageRespone();
        messageRespone.setMessage("Restaurant deleted successfully");

        return new ResponseEntity<>(messageRespone, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader("Authorization") String jwt,
                                                           @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String jwt
                                                             ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }




}
