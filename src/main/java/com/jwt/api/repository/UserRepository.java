package com.jwt.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.api.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	
    User findByUserName(String username);
   

	
}
