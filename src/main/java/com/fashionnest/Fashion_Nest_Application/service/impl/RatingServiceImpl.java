package com.fashionnest.Fashion_Nest_Application.service.impl;

import com.fashionnest.Fashion_Nest_Application.exception.ProductException;
import com.fashionnest.Fashion_Nest_Application.model.Product;
import com.fashionnest.Fashion_Nest_Application.model.Rating;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.repository.RatingRepository;
import com.fashionnest.Fashion_Nest_Application.request.RatingRequest;
import com.fashionnest.Fashion_Nest_Application.service.ProductService;
import com.fashionnest.Fashion_Nest_Application.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductService productService;
    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {

        Product product=productService.findProductById(req.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {

        return ratingRepository.getAllProductsRating(productId);
    }
}
