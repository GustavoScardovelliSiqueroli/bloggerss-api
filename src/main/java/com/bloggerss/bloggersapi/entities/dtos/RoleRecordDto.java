package com.bloggerss.bloggersapi.entities.dtos;

import com.bloggerss.bloggersapi.entities.enums.RoleName;
import jakarta.validation.constraints.NotBlank;

public record RoleRecordDto (@NotBlank RoleName roleName){
}
