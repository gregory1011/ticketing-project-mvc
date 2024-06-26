package com.cydeo.mapper;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskDTO convertToDto(Task entity){
        return modelMapper.map(entity, TaskDTO.class);
    }

    public Task convertToEntity(TaskDTO dto){
        return modelMapper.map(dto, Task.class);
    }

}
