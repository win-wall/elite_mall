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
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("is_enable")
    private boolean isEnable;
    @JsonProperty("is_delete")
    private boolean isDelete;
}
