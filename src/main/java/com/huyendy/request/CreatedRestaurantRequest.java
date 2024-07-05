package com.huyendy.request;

import com.huyendy.model.Address;
import com.huyendy.model.ContactInfomation;
import lombok.Data;

import java.util.List;

@Data
public class CreatedRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfomation contactInfomation;
    private String openingHour;
    private List<String> images;

}
