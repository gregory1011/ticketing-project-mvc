package com.cydeo.service.impl;


import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepo;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> listAllUsers() {

        // go to Db and bring all the users
        List<User> userList = userRepo.findAll();

        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        User repoUser = userRepo.findByUserName(username);
        return userMapper.convertToDTO(repoUser);
    }

    @Override
    public void save(UserDTO userDTO) {

        User user = userMapper.convertToEntity(userDTO);
        userRepo.save(user);
       // userRepo.save(userMapper.convertToEntity(userDTO));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        // we need to capture current user from DB using method findByUsername
        User user = userRepo.findByUserName(userDTO.getUserName());
        Long userId = user.getId();
        //convert userDTO to entity object
        User convertedUser = userMapper.convertToEntity(userDTO);

        // set ID to converted object to be the same
        convertedUser.setId(userId);

        // save updated user
        userRepo.save(convertedUser);

        return findByUserName(userDTO.getUserName());

    }

    @Override
    public void deleteByUserName(String username) {

        userRepo.deleteByUserName(username);
    }

    @Override
    public void delete(String username) {

        // we don't need to delete from DB any users or transactions
        // we need to change is-deleted = true
        //find user in DB
        User user = userRepo.findByUserName(username);

        // set isDeleted=true
        user.setIsDeleted(true);

        // save updatedUser in DB
        userRepo.save(user);

    }

    @Override
    public List<UserDTO> listAllManagers() {

        List<User> managerList = userRepo.findAll().stream()
                .filter(managers -> managers.getRole().getDescription().equals("Manager")).collect(Collectors.toList());

        List<UserDTO> managers = managerList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());

        return managers;
    }


}
