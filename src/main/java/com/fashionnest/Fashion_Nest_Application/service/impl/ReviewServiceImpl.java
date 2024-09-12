package com.fashionnest.Fashion_Nest_Application.service.impl;

import com.fashionnest.Fashion_Nest_Application.exception.ProductException;
import com.fashionnest.Fashion_Nest_Application.model.Product;
import com.fashionnest.Fashion_Nest_Application.model.Review;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.repository.ProductRepository;
import com.fashionnest.Fashion_Nest_Application.repository.ReviewRepository;
import com.fashionnest.Fashion_Nest_Application.request.ReviewRequest;
import com.fashionnest.Fashion_Nest_Application.service.ProductService;
import com.fashionnest.Fashion_Nest_Application.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());

        Review review=new Review();
        review.setUser(user);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
