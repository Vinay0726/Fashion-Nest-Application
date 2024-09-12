package com.fashionnest.Fashion_Nest_Application.service.impl;

import com.fashionnest.Fashion_Nest_Application.exception.ProductException;
import com.fashionnest.Fashion_Nest_Application.model.Cart;
import com.fashionnest.Fashion_Nest_Application.model.CartItem;
import com.fashionnest.Fashion_Nest_Application.model.Product;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.repository.CartRepository;
import com.fashionnest.Fashion_Nest_Application.request.AddItemRequest;
import com.fashionnest.Fashion_Nest_Application.service.CartItemService;
import com.fashionnest.Fashion_Nest_Application.service.CartService;
import com.fashionnest.Fashion_Nest_Application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;



    @Override
    public Cart createCart(User user) {

        Cart cart=new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
       Cart cart=cartRepository.findByUserId(userId);
        Product product=productService.findProductById(req.getProductId());
        CartItem isPresent=cartItemService.isCartItemExist(cart,product,req.getSize(),userId);
        if(isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price = req.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item ADD to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {

        Cart cart=cartRepository.findByUserId(userId);
        int totalPrice=0;
        int totalDiscountPrice=0;
        int totalItem=0;

        //for total price,discounted price,quantity
       for (CartItem cartItem:cart.getCartItems()){
           totalPrice=totalPrice+cartItem.getPrice();
           totalDiscountPrice=totalDiscountPrice+cartItem.getDiscountedPrice();
           totalItem=totalItem+cartItem.getQuantity();
       }
       cart.setTotalDiscountedPrice(totalDiscountPrice);
       cart.setTotalItem(totalItem);
       cart.setTotalPrice(totalPrice);
       cart.setDiscount(totalPrice-totalDiscountPrice);
        return cartRepository.save(cart);
    }
}
