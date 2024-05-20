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
    public UserDTO findUserByUserName(String username) {

        User user = userRepo.findByUserName(username);
        return userMapper.convertToDTO(user);
    }

    @Override
    public void save(UserDTO userDTO) {

//        User user = userMapper.convertToEntity(userDTO);
//        userRepo.save(user);
        userRepo.save(userMapper.convertToEntity(userDTO));
    }

    @Override
    public UserDTO update(UserDTO dto) {

        // we need to capture current user from DB using method findByUsername
        User user = userRepo.findByUserName(dto.getUserName());

        // convert userDTO to entity object
        User convertedUser = userMapper.convertToEntity(dto);

         // set ID to converted object to be the same
        convertedUser.setId(user.getId());

         // save updated user
        userRepo.save(convertedUser);

        return findUserByUserName(dto.getUserName());
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
    public List<UserDTO> listAllUsersByRole(String role) {

//        List<User> userList = userRepo.findAllByRoleDescriptionIgnoreCase(role);
//        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());

        return userRepo.findAll().stream().filter(m -> m.getRole().getDescription().equalsIgnoreCase(role))
                .map(userMapper::convertToDTO).collect(Collectors.toList());
    }


}
