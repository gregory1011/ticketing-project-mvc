package com.cydeo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public String getUsername(){
        return null;
    }

    public <T> T convert(Object fromEntity, T toDto){
        return modelMapper.map(fromEntity, (Type) toDto.getClass());
    }

//    public <T> T convertToEntity(Object fromDto, T toEntity){
//        return modelMapper.map(fromDto, (Type) toEntity.getClass());
//    }
//
//    public <T> T convertToDto(Object fromEntity, T toDto){
//        return modelMapper.map(fromEntity, (Type) toDto.getClass());
//    }

}
