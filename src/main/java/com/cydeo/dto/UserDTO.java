package com.cydeo.dto;

import com.cydeo.enums.Gender;
import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank
    @Size(min = 2, max = 15)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 15)
    private String lastName;
    @NotBlank
    @Email
    private String username;
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String password;
    @NotNull
    private String confirmPassWord;
    private boolean enabled;
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;
    @NotNull
    private RoleDTO role;
    @NotNull
    private Gender gender;
}
