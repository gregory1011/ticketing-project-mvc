package com.cydeo.dto;

import com.cydeo.enums.Gender;
import lombok.*;

import javax.validation.constraints.*;

@ToString
@Getter
@Setter
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
    private String userName;
    @NotBlank
    private String password;
    @NotNull
    private String confirmPassword;
    private boolean enabled;
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;
    @NotNull
    private RoleDTO role;
    @NotNull
    private Gender gender;

//    public String getPassWord() {
//        return password;
//    }
//
//    public void setPassWord(String passWord) {
//        this.password = password;
//        checkConfirmPassWord();
//    }
//    public String getConfirmPassWord() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassWord(String confirmPassWord) {
//        this.confirmPassword = confirmPassWord;
//        checkConfirmPassWord();
//    }
//
//    private void checkConfirmPassWord() {
//        if(this.password == null || this.confirmPassword == null){
//            return;
//        }else if(!this.password.equals(confirmPassword)){
//            this.confirmPassword = null;
//        }
//    }
//
//
//
//
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public RoleDTO getRole() {
//        return role;
//    }
//
//    public void setRole(RoleDTO role) {
//        this.role = role;
//    }
//
//    public Gender getGender() {
//        return gender;
//    }
//
//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }

}
