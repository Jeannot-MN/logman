package com.jmn.logman.model.repository;

import com.jmn.logman.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select distinct user from User user\n" +
            "where lower(user.username) = lower(:username)\n" +
            "or lower(user.email) = lower(:email)")
    User findByEmailOrUsernameIgnoreCase(@Param("email") String email, @Param("username") String username);

    User findByUsername(String username);
}
