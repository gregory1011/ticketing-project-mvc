package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@ConfigurationPropertiesBinding // we use this annotation to bind the method we override and use it at all times.
public class RoleDtoConverter implements Converter<String, RoleDTO> {
    // this class is to convert drop down object to string. we get an error after we click SAVE
    // we use Converter interface to be able to convert to string
    // we inject dependency roleService


    RoleService roleService;

    public RoleDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    // we override the method from Convert interface
    @Override
    public RoleDTO convert(String source) {

        return roleService.findById(Long.parseLong(source));
    }


}
