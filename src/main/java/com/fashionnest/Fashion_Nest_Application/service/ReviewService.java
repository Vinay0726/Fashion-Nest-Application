package com.fashionnest.Fashion_Nest_Application.service;

import com.fashionnest.Fashion_Nest_Application.exception.ProductException;
import com.fashionnest.Fashion_Nest_Application.model.Review;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user)throws ProductException;

    public List<Review> getAllReview(Long productId);
}
