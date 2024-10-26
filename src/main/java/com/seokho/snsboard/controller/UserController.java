package com.seokho.snsboard.controller;

import com.seokho.snsboard.model.entity.UserEntity;
import com.seokho.snsboard.model.post.Post;
import com.seokho.snsboard.model.user.*;
import com.seokho.snsboard.service.PostService;
import com.seokho.snsboard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String query) {
        var users = userService.getUsers(query);
        return ResponseEntity.ok(users);

    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        var user = userService.getUser(username);
        return ResponseEntity.ok(user);

    }

    @PatchMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username,
                                           @RequestBody UserPatchRequestBody requestBody,
                                           Authentication authentication) {
        var user = userService.updateUser(username, requestBody, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(user);

    }
    //GET /users/{username}/posts
    @GetMapping("/{username}/posts")
    public ResponseEntity<List<Post>> getPostsByUsername(@PathVariable String username) {
        var posts= postService.getPostsByUsername(username);

        return ResponseEntity.ok(posts);

    }

    @PostMapping
    public ResponseEntity<User> singUp(@Valid @RequestBody UserSignUpRequestBody userSignUpRequestBody){
       var user =  userService.signUp(
               userSignUpRequestBody.username(),
               userSignUpRequestBody.password()
       );

       return  ResponseEntity.ok(user);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(@Valid @RequestBody UserLoginRequestBody userLoginRequestBody){
        var response =  userService.authenticate(
                userLoginRequestBody.username(),
                userLoginRequestBody.password()
        );

        return  ResponseEntity.ok(response);

    }
}
