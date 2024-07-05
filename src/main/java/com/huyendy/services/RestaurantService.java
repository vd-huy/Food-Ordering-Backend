package com.huyendy.services;

import com.huyendy.dto.RestaurantDto;
import com.huyendy.model.Restaurant;
import com.huyendy.model.User;
import com.huyendy.request.CreatedRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createdRestaurant(CreatedRestaurantRequest req, User user);

    public Restaurant updateRestaurant (Long restaurantId, CreatedRestaurantRequest req) throws Exception;

    public void deleteRestaurant(Long restaurantId ) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
