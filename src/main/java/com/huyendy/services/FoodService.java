package com.huyendy.services;

import com.huyendy.model.Category;
import com.huyendy.model.Food;
import com.huyendy.model.Restaurant;
import com.huyendy.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegitarain,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long id) throws Exception;

    public Food updateAvailibilityStatus(Long foodId) throws Exception;
}
