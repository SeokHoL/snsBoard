package com.seokho.snsboard.model.user;


import jakarta.validation.constraints.NotEmpty;

public record UserSignUpRequestBody(@NotEmpty String username, @NotEmpty String password) {}