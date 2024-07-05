package com.huyendy.respones;

import com.huyendy.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthRespone {

    private String jwt;
    private String message;

    private USER_ROLE role;
}
