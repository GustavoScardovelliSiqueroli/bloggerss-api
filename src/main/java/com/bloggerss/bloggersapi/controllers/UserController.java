package com.bloggerss.bloggersapi.controllers;

import com.bloggerss.bloggersapi.business.UserService;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.entities.dtos.LoginResponseDto;
import com.bloggerss.bloggersapi.entities.dtos.UserRecordDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<UserModel> registerUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerPublicUser(userRecordDto));
    }

    @PostMapping("authenticate")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid UserRecordDto authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.authenticate(authentication));
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

}
