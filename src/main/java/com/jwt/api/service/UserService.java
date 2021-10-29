package com.jwt.api.service;

import java.util.List;
import java.util.Optional;

import com.jwt.api.entity.User;



public interface UserService {

	
	 
	 public User insertStudent(User student);
	 
	 public List<User> retriveAllUsers();
	 
	 public Optional<User> findUser(int uid);
	 
	 public User findbyUsername(String username);
	 
	
	 
}
