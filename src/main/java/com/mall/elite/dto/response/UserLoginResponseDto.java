package com.mall.elite.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginResponseDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @JsonProperty("token_access")
    private String tokenAccess;
    @JsonProperty("token_refresh")
    private String tokenRefresh;
}
