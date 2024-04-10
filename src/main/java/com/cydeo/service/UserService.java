package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService extends CrudService <UserDTO, String> {

//    // save user, findById user, delete user, findAll user
//    UserDTO save(UserDTO user);
//    UserDTO findById(String username);
//    List<UserDTO> findAll();
//    void delete(UserDTO user);
//    void deleteById(String username);

    List<UserDTO> findManager();
    List<UserDTO> findEmployee();

}
