package com.bloggerss.bloggersapi.controllers;

import com.bloggerss.bloggersapi.dtos.PostRecordDto;
import com.bloggerss.bloggersapi.entities.PostModel;
import com.bloggerss.bloggersapi.repositories.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @PostMapping("/")
    public ResponseEntity<PostModel> createShirt(@RequestBody @Valid PostRecordDto postRecordDto) {
        var newPost = new PostModel();
        BeanUtils.copyProperties(postRecordDto, newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(newPost));

    }

}

