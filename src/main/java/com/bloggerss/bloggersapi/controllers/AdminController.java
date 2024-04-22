package com.bloggerss.bloggersapi.controllers;

import com.bloggerss.bloggersapi.business.RoleService;
import com.bloggerss.bloggersapi.business.UserService;
import com.bloggerss.bloggersapi.entities.RoleModel;
import com.bloggerss.bloggersapi.entities.UserModel;
import com.bloggerss.bloggersapi.entities.dtos.RoleRecordDto;
import com.bloggerss.bloggersapi.entities.enums.RoleName;
import com.bloggerss.bloggersapi.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("adm")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("roles")
    public ResponseEntity<List<RoleModel>> getRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAllRoles());
    }

    @PostMapping("roles/register")
    public ResponseEntity<RoleModel> registerRole(@RequestBody RoleRecordDto roleRecordDto){
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.registerRole(roleRecordDto));
        } catch (UsernameNotFoundException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (SQLIntegrityConstraintViolationException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("give/{username}")
    public ResponseEntity<UserModel> giveAdmin(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.setUserADM(username));
    }

    @PutMapping("creator/give/{username}")
    public ResponseEntity<UserModel> giveCreator(@PathVariable String username) {
        try{
        return ResponseEntity.status(HttpStatus.OK).body(userService.setUserCreator(username));
        }catch (UsernameNotFoundException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
