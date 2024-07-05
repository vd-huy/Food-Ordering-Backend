package com.huyendy.controller;

import com.huyendy.model.IngredientsCategory;
import com.huyendy.model.IngredientsItem;
import com.huyendy.request.IngredientCategoryRequest;
import com.huyendy.request.IngredientRequest;
import com.huyendy.services.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredient")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;


    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req

            ) throws Exception {

        IngredientsCategory item = ingredientsService.createIngredientsCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest req

    ) throws Exception {

        IngredientsItem item = ingredientsService.createIngredientItem( req.getRestaurantId(),req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {

        IngredientsItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {

        List<IngredientsItem> items = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {

        List<IngredientsCategory> items = ingredientsService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


}
