package com.mall.elite.dto.request;

import com.mall.elite.Entity.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResigterDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Collection<Role> roles;
}
