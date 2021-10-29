package com.jwt.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.api.entity.User;
import com.jwt.api.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repo;

	static{
		System.out.println("user impl");
	}
	

	@Override
	public User insertStudent(User student) {
	
		return repo.save(student);
	}



	public List<User> retriveAllUsers() {
		return repo.findAll();
	}



	@Override
	public Optional<User> findUser(int uid) {
		
		return repo.findById(uid);
	}



	@Override
	public User findbyUsername(String username) {
		
		return repo.findByUserName(username);
	}



	

	
	
	
}
