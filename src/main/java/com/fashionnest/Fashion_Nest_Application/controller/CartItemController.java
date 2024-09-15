package com.fashionnest.Fashion_Nest_Application.controller;

import com.fashionnest.Fashion_Nest_Application.exception.CartItemException;
import com.fashionnest.Fashion_Nest_Application.exception.ProductException;
import com.fashionnest.Fashion_Nest_Application.exception.UserException;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.request.AddItemRequest;
import com.fashionnest.Fashion_Nest_Application.response.ApiResponse;
import com.fashionnest.Fashion_Nest_Application.service.CartItemService;
import com.fashionnest.Fashion_Nest_Application.service.CartService;
import com.fashionnest.Fashion_Nest_Application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
                                                     @RequestHeader("Authorization")String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(), req);

        ApiResponse res = new ApiResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{productId}")
    @Operation(description = "Remove item from cart by product ID")
    public ResponseEntity<ApiResponse> removeCartItem(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long productId) throws UserException, CartItemException {

        // Find the user by JWT token
        User user = userService.findUserProfileByJwt(jwt);

        // Call the service to remove the cart item by productId
        ApiResponse response = cartItemService.removeCardItem(user.getId(), productId);
        ApiResponse res = new ApiResponse();
        res.setMessage("item remove from cart");
        res.setStatus(true);

        // Return the response
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
