package com.bloggerss.bloggersapi.entities.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record PostRecordDto(@NotBlank String title, @NotBlank String content) {

}
