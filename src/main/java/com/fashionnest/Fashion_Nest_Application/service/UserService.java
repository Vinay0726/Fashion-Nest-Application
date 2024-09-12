package com.fashionnest.Fashion_Nest_Application.service;

import com.fashionnest.Fashion_Nest_Application.exception.UserException;
import com.fashionnest.Fashion_Nest_Application.model.User;
import jdk.jshell.spi.ExecutionControl;

public interface UserService {

    public User findUserBYId(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

}