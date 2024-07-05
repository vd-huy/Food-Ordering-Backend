package com.huyendy.repository;

import com.huyendy.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {

    List<Food> findByRestaurantId(Long restaurantId);

    @Query("Select f From Food f Where f.name Like %:keyword% Or f.foodCategory.name Like %:keyword%")
    List<Food> searchFood(@Param("keyword") String keyword);
}
