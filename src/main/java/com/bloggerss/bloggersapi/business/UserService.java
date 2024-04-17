package com.bloggerss.bloggersapi.business;

import com.bloggerss.bloggersapi.entities.RoleModel;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.entities.dtos.UserRecordDto;
import com.bloggerss.bloggersapi.enums.RoleName;
import com.bloggerss.bloggersapi.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel registerPublicUser(UserRecordDto userRecordDto){
        var newUser = new UserModel();
        BeanUtils.copyProperties(userRecordDto, newUser);

        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();
        String passwordEncrypted = encrypt.encode(newUser.getPassword());

        RoleModel roleUser = new RoleModel();
        roleUser.setRoleId(UUID.fromString("8c25f4c7-563f-4931-8f0c-ab5f53101f87"));
        roleUser.setRoleName(RoleName.ROLE_USER);

        newUser.setRoles(List.of(roleUser));
        newUser.setPassword(passwordEncrypted);
        userRepository.save(newUser);

        return newUser;
    }

}
