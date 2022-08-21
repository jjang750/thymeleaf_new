package com.aegisep.thymeleaf.repository;

import com.aegisep.thymeleaf.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * From User where token =?1",
            nativeQuery = true)
    User findByToken(String token);

    @Query(value = "SELECT * FROM User u WHERE u.user_id = ?1",
            nativeQuery = true)
    User findByUserid(String userid);

    @Query(value = "select * From User u where user_id =?1 and passwd = md5(?2)",
            nativeQuery = true)
    User findByUsernameAndPassword(String username, String passwd);
}
