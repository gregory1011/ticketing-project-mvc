package com.cydeo.dto;

import com.cydeo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firsName, lastName, username, password;
    private boolean enabled;
    private String phone;
    private RoleDTO role;
    private Gender gender;
}
