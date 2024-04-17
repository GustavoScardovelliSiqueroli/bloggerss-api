package com.bloggerss.bloggersapi.entities.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(@NotBlank String username, @NotBlank String password) {
}
