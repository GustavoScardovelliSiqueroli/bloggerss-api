package com.bloggerss.bloggersapi.config.security;

import com.bloggerss.bloggersapi.entities.UserAuthenticated;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(UserAuthenticated::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + username));
    }
}