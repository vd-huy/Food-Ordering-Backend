package com.huyendy.controller;

import com.huyendy.config.JwtProvider;
import com.huyendy.model.Cart;
import com.huyendy.model.USER_ROLE;
import com.huyendy.model.User;
import com.huyendy.repository.CartRepository;
import com.huyendy.repository.UserRepository;
import com.huyendy.request.LoginRequest;
import com.huyendy.respones.AuthRespone;
import com.huyendy.services.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthRespone> createUserHandle(@RequestBody User user) throws Exception {

        User isEmailExits = userRepository.findByEmail(user.getEmail());
        if (isEmailExits!=null){
            throw new Exception("Email is  already used");
        }

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthRespone authRespone = new AuthRespone();
        authRespone.setJwt(jwt);
        authRespone.setMessage("Register success");
        authRespone.setRole(savedUser.getRole());

        return new ResponseEntity<>(authRespone, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthRespone> signin(@RequestBody LoginRequest request){

        String email = request.getEmail();
        String password = request.getPassword();


        Authentication authentication = authenticate(email,password);

        Collection<?extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthRespone authRespone = new AuthRespone();
        authRespone.setJwt(jwt);
        authRespone.setMessage("Login success");

        authRespone.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authRespone,HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(email);
        if (userDetails == null){
            throw new BadCredentialsException("Invalid email,...");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
