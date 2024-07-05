package com.huyendy.services;

import com.huyendy.model.IngredientsCategory;
import com.huyendy.model.IngredientsItem;
import com.huyendy.model.Restaurant;
import com.huyendy.repository.IngredientCategoryRepository;
import com.huyendy.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientsService  {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;


    @Override
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientsCategory category = new IngredientsCategory();
        category.setRestaurant(restaurant);
        category.setName(name);


        return ingredientCategoryRepository.save(category);
    }

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception {

        Optional<IngredientsCategory> otp = ingredientCategoryRepository.findById(id);
        if (otp.isEmpty()) {
            throw new Exception("Ingredient category not found");
        }

        return otp.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {

        restaurantService.findRestaurantById(id);

        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory category = findIngredientsCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);


        IngredientsItem ingredientsItemSave = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredientsItemSave);
        return ingredientsItemSave;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {

        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);

        if (optionalIngredientsItem.isEmpty()) {
            throw new Exception("Ingredient not found");
        }

        IngredientsItem ingredientsItem = optionalIngredientsItem.get();

        ingredientsItem.setStoke(!ingredientsItem.isStoke());

        return ingredientItemRepository.save(ingredientsItem);
    }
}
