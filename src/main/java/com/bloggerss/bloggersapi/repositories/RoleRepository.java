package com.bloggerss.bloggersapi.repositories;

import com.bloggerss.bloggersapi.entities.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
}
