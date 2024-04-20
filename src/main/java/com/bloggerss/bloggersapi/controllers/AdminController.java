package com.bloggerss.bloggersapi.controllers;

import com.bloggerss.bloggersapi.business.UserService;
import com.bloggerss.bloggersapi.entities.RoleModel;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("adm")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("roles")
    public List<RoleModel> getRoles(){
        return roleRepository.findAll();
    }

    @GetMapping
    public String test(){
        return "test";
    }

    @PutMapping("give/{username}")
    public UserModel giveAdmin(@PathVariable String username){
        return userService.setUserADM(username);
    }


}
