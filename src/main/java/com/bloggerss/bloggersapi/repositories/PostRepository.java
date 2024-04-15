package com.bloggerss.bloggersapi.repositories;

import com.bloggerss.bloggersapi.entities.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostModel, UUID> {
}
