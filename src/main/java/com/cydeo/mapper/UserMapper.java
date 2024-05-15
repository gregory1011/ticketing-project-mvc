package com.cydeo.mapper;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public  User convertToEntity(UserDTO dto){
        return modelMapper.map(dto,User.class);
    }

    public UserDTO convertToDTO(User entity){
        return modelMapper.map(entity,UserDTO.class);
    }

}
