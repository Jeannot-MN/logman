package com.jmn.logman.model.repository;

import com.jmn.logman.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    UserRole findByUserIdAndRoleId(Long userId, String roleId);
}
