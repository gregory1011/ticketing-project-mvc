package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {


//    @Query(value = "select * from users where user_name= ?1", nativeQuery = true)
    User findByUserName(String username);
    @Transactional
    void deleteByUserName(String username);

    List<User> findAllByRoleDescriptionIgnoreCase(String description);
}
