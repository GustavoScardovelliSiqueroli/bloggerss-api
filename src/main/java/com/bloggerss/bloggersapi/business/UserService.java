package com.bloggerss.bloggersapi.business;

import com.bloggerss.bloggersapi.config.security.JwtService;
import com.bloggerss.bloggersapi.entities.RoleModel;
import com.bloggerss.bloggersapi.entities.UserAuthenticated;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.entities.dtos.LoginResponseDTO;
import com.bloggerss.bloggersapi.entities.dtos.UserRecordDto;
import com.bloggerss.bloggersapi.enums.RoleName;
import com.bloggerss.bloggersapi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Transactional
    public LoginResponseDTO authenticate (UserRecordDto userRecordDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(userRecordDto.username(), userRecordDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var userAuth = auth.getPrincipal();

        var token = jwtService.generateToken((UserAuthenticated) userAuth);

        return new LoginResponseDTO(token);
    }

    public UserModel registerPublicUser(UserRecordDto userRecordDto) {
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

    public UserModel setUserADM(String username) {
        System.out.println(username);

        Optional<UserModel> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("Username not found.");

        UserModel userFound = user.get();

        RoleModel admRole = new RoleModel();
        admRole.setRoleId(UUID.fromString("f0acc290-c6cc-4017-b4d3-257a6bde1bb9"));
        admRole.setRoleName(RoleName.ROLE_ADMIN);

        RoleModel userRole = new RoleModel();
        userRole.setRoleId(UUID.fromString("8c25f4c7-563f-4931-8f0c-ab5f53101f87"));
        userRole.setRoleName(RoleName.ROLE_USER);

        List<RoleModel> listRole = new ArrayList<RoleModel>();
        listRole.add(admRole);
        listRole.add(userRole);

        userFound.setRoles(listRole);

        return userRepository.save(userFound);
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }
}
