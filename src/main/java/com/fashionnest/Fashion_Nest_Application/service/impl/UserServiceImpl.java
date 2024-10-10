package com.fashionnest.Fashion_Nest_Application.service.impl;

import com.fashionnest.Fashion_Nest_Application.config.JwtProvider;
import com.fashionnest.Fashion_Nest_Application.exception.UserException;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.repository.UserRepository;
import com.fashionnest.Fashion_Nest_Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserBYId(Long userId) throws UserException {
       Optional<User> user=userRepository.findById(userId);

       if(user.isPresent()){
           return user.get();
       }throw new UserException("user not fount with id"+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email=jwtProvider.getEmailFromToken(jwt);

        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new UserException("user not found with email"+email);
        }
        return user;
    }

    @Override
    public User findUserById(Long userId)throws UserException {
        Optional<User> user=userRepository.findById(userId);

        if(user.isPresent()){
            return user.get();
        }throw new UserException("user not fount with id"+userId);
    }
}
