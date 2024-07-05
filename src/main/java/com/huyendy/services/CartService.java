package com.huyendy.services;

import com.huyendy.model.Cart;
import com.huyendy.model.CartItem;
import com.huyendy.model.User;
import com.huyendy.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    public Long caculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(User user) throws Exception;

    public Cart clearCart(String jwt) throws Exception;
}
