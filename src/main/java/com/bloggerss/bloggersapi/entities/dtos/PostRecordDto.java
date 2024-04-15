package com.bloggerss.bloggersapi.entities.dtos;

import jakarta.validation.constraints.NotBlank;

public record PostRecordDto(@NotBlank String title, @NotBlank String content) {

}
