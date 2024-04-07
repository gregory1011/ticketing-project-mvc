package com.cydeo.service.impl;

import com.cydeo.bootstrap.DataGenerator;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AbstractMapService<UserDTO, String> implements UserService {

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

    @Override
    public void update(UserDTO object) {
        super.update(object.getUsername(), object);
    }

    @Override
    public List<UserDTO> findManager() {
        return super.findAll().stream()
                .filter(p -> p.getRole().getId() == 2)
                .collect(Collectors.toList());
    }
}
