package com.fashionnest.Fashion_Nest_Application.service;

import com.fashionnest.Fashion_Nest_Application.exception.ProductException;
import com.fashionnest.Fashion_Nest_Application.model.Rating;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user)throws ProductException;

    public List<Rating> getProductsRating(Long productId);

}
