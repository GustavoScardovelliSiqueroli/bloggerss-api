package com.bloggerss.bloggersapi.repositories;

import com.bloggerss.bloggersapi.entities.RoleModel;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.entities.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    Optional<RoleModel> findByRoleName(RoleName roleName);

}
