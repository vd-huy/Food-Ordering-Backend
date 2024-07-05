package com.huyendy.request;

import com.huyendy.model.Category;
import com.huyendy.model.IngredientsItem;
import com.huyendy.model.Restaurant;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category foodCategory;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarin;
    private boolean seasional;
    private List<IngredientsItem> ingredients;

}
