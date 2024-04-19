package com.bloggerss.bloggersapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("adm")
public class AdminController {

    @GetMapping
    public String test(){
        return "test";
    }
}
