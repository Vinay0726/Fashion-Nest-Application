package com.fashionnest.Fashion_Nest_Application.service.impl;

import com.fashionnest.Fashion_Nest_Application.exception.CartItemException;
import com.fashionnest.Fashion_Nest_Application.exception.UserException;
import com.fashionnest.Fashion_Nest_Application.model.Cart;
import com.fashionnest.Fashion_Nest_Application.model.CartItem;
import com.fashionnest.Fashion_Nest_Application.model.Product;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.repository.CardItemRepository;
import com.fashionnest.Fashion_Nest_Application.repository.CartRepository;
import com.fashionnest.Fashion_Nest_Application.service.CartItemService;
import com.fashionnest.Fashion_Nest_Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CardItemRepository cardItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;


    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);

        //5*10=50
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        CartItem createdCardItem=cardItemRepository.save(cartItem);
        return createdCardItem;
    }

    @Override
    public CartItem updateCartItem(Long userId,Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item=findCartItemById(id);
        User user=userService.findUserBYId(item.getUserId());

        if(user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());

        }
      return   cardItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

        CartItem cartItem=cardItemRepository.isCartItemExist(cart,product,size,userId);

        return cartItem;
    }

    @Override
    public void removeCardItem(Long userId, Long cardItemId) throws CartItemException, UserException {

        CartItem cartItem=findCartItemById(cardItemId);
        User user=userService.findUserBYId(cartItem.getUserId());

        User reqUser=userService.findUserBYId(userId);

        if(user.getId().equals(reqUser.getId())){
            cardItemRepository.deleteById(cardItemId);
        }
        else {
            throw new UserException(("you can't remove another users item"));
        }
    }

    @Override
    public CartItem findCartItemById(Long cardItemId) throws CartItemException {

        Optional<CartItem> cartItemOptional=cardItemRepository.findById(cardItemId);
        if(cartItemOptional.isPresent()){
            return cartItemOptional.get();
        }throw new CartItemException("CartItem not Found with id"+cardItemId);

    }
}
