package com.bloggerss.bloggersapi.business;

import com.bloggerss.bloggersapi.config.security.JwtService;
import com.bloggerss.bloggersapi.entities.RoleModel;
import com.bloggerss.bloggersapi.entities.UserAuthenticated;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.entities.dtos.LoginResponseDto;
import com.bloggerss.bloggersapi.entities.dtos.RoleRecordDto;
import com.bloggerss.bloggersapi.entities.dtos.UserRecordDto;
import com.bloggerss.bloggersapi.entities.enums.RoleName;
import com.bloggerss.bloggersapi.repositories.RoleRepository;
import com.bloggerss.bloggersapi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RoleService roleService;
    private List<RoleModel> newRoles;

    @Transactional
    public LoginResponseDto authenticate(UserRecordDto userRecordDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userRecordDto.username(), userRecordDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var userAuth = auth.getPrincipal();

        var token = jwtService.generateToken((UserAuthenticated) userAuth);

        return new LoginResponseDto(token);
    }

    public UserModel registerPublicUser(UserRecordDto userRecordDto) throws SQLIntegrityConstraintViolationException {
        var newUser = new UserModel();
        BeanUtils.copyProperties(userRecordDto, newUser);

        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();
        String passwordEncrypted = encrypt.encode(newUser.getPassword());

        Optional<RoleModel> role = roleRepository.findByRoleName(RoleName.ROLE_USER);
        if (role.isEmpty()) {
            RoleModel roleUser = roleService.registerRole(new RoleRecordDto(RoleName.ROLE_USER));
            newUser.setRoles(List.of(roleUser));
        } else {
            newUser.setRoles(List.of(role.get()));
        }

        newUser.setPassword(passwordEncrypted);
        userRepository.save(newUser);

        return newUser;
    }

    public UserModel setUserADM(String username) throws SQLIntegrityConstraintViolationException {
        Optional<UserModel> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("Username not found.");

        UserModel userFound = user.get();

        List<RoleModel> newRoles = userFound.getRoles();
        Optional<RoleModel> role = roleRepository.findByRoleName(RoleName.ROLE_ADMIN);
        if (role.isEmpty()) {
            RoleModel admRole = roleService.registerRole(new RoleRecordDto(RoleName.ROLE_ADMIN));
            newRoles.add(admRole);
        } else {
            newRoles.add(role.get());
        }

        userFound.setRoles(newRoles);

        return userRepository.save(userFound);
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public UserModel setUserCreator(String username) {
        Optional<UserModel> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("Username not found.");

        UserModel userFound = user.get();

        List<RoleModel> roles = userFound.getRoles();

        RoleModel creatorRole = new RoleModel();
        creatorRole.setRoleId(UUID.fromString("c33db4bd-db0a-40f7-b0e3-0340b74f2f37"));
        creatorRole.setRoleName(RoleName.ROLE_CREATOR);

        roles.add(creatorRole);

        userFound.setRoles(roles);

        return userRepository.save(userFound);
    }

}
