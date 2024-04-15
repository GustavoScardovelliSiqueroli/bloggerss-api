package com.bloggerss.bloggersapi.controllers;

import com.bloggerss.bloggersapi.business.PostService;
import com.bloggerss.bloggersapi.dtos.PostRecordDto;
import com.bloggerss.bloggersapi.entities.PostModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/")
    public ResponseEntity<PostModel> createShirt(@RequestBody @Valid PostRecordDto postRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRecordDto));
    }

    @GetMapping
    public ResponseEntity<List<PostModel>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.findByID(id));
        } catch (ChangeSetPersister.NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
    }

}

