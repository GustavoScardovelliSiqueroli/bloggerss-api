package com.bloggerss.bloggersapi.business;

import com.bloggerss.bloggersapi.entities.RoleModel;
import com.bloggerss.bloggersapi.entities.dtos.RoleRecordDto;
import com.bloggerss.bloggersapi.entities.enums.RoleName;
import com.bloggerss.bloggersapi.repositories.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleModel registerRole(RoleRecordDto roleRecordDto) throws SQLIntegrityConstraintViolationException {
        RoleModel roleModel = new RoleModel();
        BeanUtils.copyProperties(roleRecordDto, roleModel);
        return roleRepository.save(roleModel);
    }

    public List<RoleModel> findAllRoles(){
        return roleRepository.findAll();
    }

}
