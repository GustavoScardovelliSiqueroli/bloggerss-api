package com.bloggerss.bloggersapi.controllers;

import com.bloggerss.bloggersapi.business.UserService;
import com.bloggerss.bloggersapi.entities.RoleModel;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.entities.dtos.LoginResponseDTO;
import com.bloggerss.bloggersapi.entities.dtos.UserRecordDto;
import com.bloggerss.bloggersapi.repositories.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public UserModel registerUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        return userService.registerPublicUser(userRecordDto);
    }

    @PostMapping("authenticate")
    public LoginResponseDTO authenticate(@RequestBody @Valid UserRecordDto authentication) {
        return userService.authenticate(authentication);
    }

    @GetMapping
    public List<UserModel> getUsers(){
        return userService.getUsers();
    }

}