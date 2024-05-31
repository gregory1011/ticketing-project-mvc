package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@Where(clause = "is_deleted=false") // this query will be concatenated everytime we call a query from userRepo
// example: Select * from USERS where is_deleted = false; // findAll();
public class User extends BaseEntity{

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

//    @Override
//    public String toString() {
//        return "User{" +
//                "firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", userName='" + userName + '\'' +
//                ", passWord='" + passWord + '\'' +
//                ", enabled=" + enabled +
//                ", phone='" + phone + '\'' +
//                ", gender=" + gender +
//                '}';
//    }

}
