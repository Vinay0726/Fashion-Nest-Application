package com.fashionnest.Fashion_Nest_Application.service;

import com.fashionnest.Fashion_Nest_Application.exception.CartItemException;
import com.fashionnest.Fashion_Nest_Application.exception.UserException;
import com.fashionnest.Fashion_Nest_Application.model.Cart;
import com.fashionnest.Fashion_Nest_Application.model.CartItem;
import com.fashionnest.Fashion_Nest_Application.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId,Long id,CartItem cartItem)throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size,Long userId);

    public void removeCardItem(Long userId,Long cardItemId)throws CartItemException, UserException;

    public CartItem findCartItemById(Long cardItemId)throws CartItemException;

}
