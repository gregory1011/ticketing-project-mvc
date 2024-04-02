package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;

import java.util.List;

public class UserServiceImpl extends AbstractMapService<String, UserDTO> implements UserService {

    @Override
    public UserDTO save(UserDTO object) {
        return super.save(object.getUsername(), object);
    }

    @Override
    public List<UserDTO> findAll() {
        return super.findAll();
    }

    @Override
    public UserDTO findById(String  id) {
        return super.findById(id);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }
}
