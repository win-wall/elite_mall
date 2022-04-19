package com.mall.elite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserLoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
