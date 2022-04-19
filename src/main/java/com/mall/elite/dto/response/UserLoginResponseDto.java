package com.mall.elite.dto.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginResponseDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String tokenAccess;
}
