package com.huyendy.request;

import com.huyendy.model.Address;
import lombok.Data;

@Data
public class OrderRequset {

    private Long restaurantId;
    private Address deliveryAddress;

}
