package com.fashionnest.Fashion_Nest_Application.repository;

import com.fashionnest.Fashion_Nest_Application.model.Cart;
import com.fashionnest.Fashion_Nest_Application.model.CartItem;
import com.fashionnest.Fashion_Nest_Application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardItemRepository extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci From CartItem ci Where ci.cart =: cart And ci.product =: product And ci.size =: size And ci.userId =: userId")
            public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product") Product product, @Param("size")String size,
                                            @Param("userId")Long userId);
}
