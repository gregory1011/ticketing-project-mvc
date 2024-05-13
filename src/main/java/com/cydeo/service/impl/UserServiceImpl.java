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

        List<User> userList = userRepo.findAll();

        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUsername(String username) {

        User repoUser = userRepo.findByUsername(username);

        return userMapper.convertToDTO(repoUser);
    }

    @Override
    public void save(UserDTO userDTO) {
        User entity = userMapper.convertToEntity(userDTO);
        userRepo.save(entity);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        // we need to capture current username
        User user = userRepo.findByUsername(userDTO.getUsername());

        //convert user to dto
        User convertedUser = userMapper.convertToEntity(userDTO);

        // set ID to converted object
        convertedUser.setId(user.getId());

        // save updated user
        userRepo.save(convertedUser);

        return findByUsername(userDTO.getUsername());
    }

    @Override
    public void deleteByUsername(String username) {
        userRepo.deleteByUsername(username);
    }

    @Override
    public void delete(String username) {

    }
}
