package com.cydeo.entity;

import com.cydeo.entity.common.UserPrincipal;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class BaseEntityListener extends AuditingEntityListener {


    @PrePersist // this annotation means to save data in DB
    public void onPersist(BaseEntity baseEntity){

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.insertDateTime= LocalDateTime.now();
        baseEntity.lastUpdateDateTime= LocalDateTime.now();
//        baseEntity.insertUserId= 1L;  // '1L' is temporary
//        baseEntity.lastUpdateUserId= 1L;  // '1L' is temporary

        // both condition must be true in order to baseEntity.insertUserId = with Principal.getId();

        if (authentication != null && !authentication.getName().equals("anonymousUser")){
            Object principal = authentication.getPrincipal();
            // we are casting
            baseEntity.insertUserId = ((UserPrincipal) principal).getId();
            baseEntity.lastUpdateUserId = ((UserPrincipal) principal).getId();
        }

    }

    @PreUpdate // this annotation will update data in DB
    public void onPreUpdate(BaseEntity baseEntity) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.lastUpdateDateTime = LocalDateTime.now();
//        baseEntity.lastUpdateUserId = 1L;


        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            Object principal = authentication.getPrincipal();
            // we are casting
            baseEntity.lastUpdateUserId = ((UserPrincipal) principal).getId();
        }

    }

}
