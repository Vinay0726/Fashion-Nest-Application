package com.fashionnest.Fashion_Nest_Application.controller;
import com.fashionnest.Fashion_Nest_Application.config.JwtProvider;
import com.fashionnest.Fashion_Nest_Application.exception.UserException;
import com.fashionnest.Fashion_Nest_Application.model.Product;
import com.fashionnest.Fashion_Nest_Application.model.User;
import com.fashionnest.Fashion_Nest_Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to find user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        try {
            User user = userService.findUserBYId(id);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            return ResponseEntity.status(404).body(null);  // Return 404 if user not found
        }
    }

    // Endpoint to find user profile by JWT token
    @GetMapping("/profile")
    public ResponseEntity<User> findUserProfileByJwt(@RequestHeader("Authorization") String jwt)throws UserException {


            User user = userService.findUserProfileByJwt(jwt);
            return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);

    }
}