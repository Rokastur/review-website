package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles WHERE role =:name", nativeQuery = true)
    Role getOneByName(String name);
}
