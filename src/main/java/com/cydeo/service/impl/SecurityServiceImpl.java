package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepo;
import com.cydeo.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserName(username);

        // validation is user is null throw exception
        if(user == null){
            throw new UsernameNotFoundException("This user does not exists");
        }

        // return convert userEntity to UserPrincipal - accept user from DB
        return new UserPrincipal(user);
    }




}
