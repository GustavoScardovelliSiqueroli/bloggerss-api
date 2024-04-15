package com.bloggerss.bloggersapi.business;

import com.bloggerss.bloggersapi.dtos.PostRecordDto;
import com.bloggerss.bloggersapi.entities.PostModel;
import com.bloggerss.bloggersapi.repositories.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostModel createPost(PostRecordDto postRecordDto) {
        PostModel newPost = new PostModel();
        BeanUtils.copyProperties(postRecordDto, newPost);
        return postRepository.save(newPost);
    }

    public List<PostModel> findAll() {
        return postRepository.findAll();
    }

    public Optional<PostModel> findByID(UUID id) throws ChangeSetPersister.NotFoundException {
            return Optional.ofNullable(postRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new));
    }
}
