package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepo;
import com.cydeo.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // instead of @Component
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDTO> listAllRoles() {

        List<Role> roleList = roleRepo.findAll();

        return roleList.stream().map(roleMapper::convertToDTO).collect(Collectors.toList());

    }

    @Override
    public RoleDTO findById(Long id) {

        return roleMapper.convertToDTO(roleRepo.findById(id).get());
    }

}
