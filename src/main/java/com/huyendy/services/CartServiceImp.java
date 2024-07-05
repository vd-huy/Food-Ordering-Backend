package com.huyendy.services;

import com.huyendy.model.Cart;
import com.huyendy.model.CartItem;
import com.huyendy.model.Food;
import com.huyendy.model.User;
import com.huyendy.repository.CartItemRepository;
import com.huyendy.repository.CartRepository;
import com.huyendy.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CartServiceImp implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;



    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Food food= foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItems()){
            if (cartItem.getCart().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CartItem saveCartItem = cartItemRepository.save(newCartItem);

        cart.getItems().add(saveCartItem);

        return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart item not found");
        }

        CartItem item = cartItemOptional.get();
        item.setQuantity((quantity));

        item.setTotalPrice(item.getFood().getPrice()*quantity);

        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart item not found");
        }

        CartItem item = cartItemOptional.get();

        cart.getItems().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long caculateCartTotals(Cart cart) throws Exception {

        Long total = 0L;

        for (CartItem cartItem : cart.getItems()){
            total+= cartItem.getFood().getPrice()* cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Optional<Cart> optionalCart = cartRepository.findById(id);

        if (optionalCart.isEmpty()){
            throw new Exception("Cart not found with id:" + id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(User user) throws Exception {

        Cart cart = cartRepository.findByCustomerId(user.getId());
        cart.setTotal(caculateCartTotals(cart));

        return cart;
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Cart cart = findCartByUserId(user);

        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
