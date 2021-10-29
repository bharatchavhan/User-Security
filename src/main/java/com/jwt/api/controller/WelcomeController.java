package com.jwt.api.controller;

import com.jwt.api.entity.AuthRequest;
import com.jwt.api.entity.User;
import com.jwt.api.repository.UserRepository;
import com.jwt.api.service.UserServiceImpl;
import com.jwt.api.util.JwtUtil;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	static {
		System.out.println("user controller");
	}

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private UserServiceImpl userservice;
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome !!";
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getall")
	public ResponseEntity<?> getalluser(@RequestBody AuthRequest authRequest) {
		String token = jwtutil.generateToken(authRequest.getUserName());
		String username = jwtutil.extractUsername(token);
		User u1 = userservice.findbyUsername(username);
		if (u1.getRole().getRoles().equals("Admin")) {
			return new ResponseEntity<>(userservice.retriveAllUsers(), HttpStatus.OK);
		}

		return new ResponseEntity<>("forbidden", HttpStatus.FORBIDDEN);

	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getuser/{Uid}")
	public ResponseEntity<?> getUser(@PathVariable("Uid") int uid, @RequestBody AuthRequest authRequest) {
		String token = jwtutil.generateToken(authRequest.getUserName());
		String username = jwtutil.extractUsername(token);
		User u1 = userservice.findbyUsername(username);
		if (u1.getRole().getRoles().equals("Admin")) {
			Optional<User> user = userservice.findUser(uid);
			if (user == null) {
				return new ResponseEntity<String>("no records with given id", HttpStatus.OK);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>("forbidden", HttpStatus.FORBIDDEN);

	}

	@PostMapping("/insert")
	public ResponseEntity<?> addStudent(@RequestBody User student) {

		if (student.getId() == 0) {
			return new ResponseEntity<>("invalid user type", HttpStatus.OK);
		} else {
			System.out.println(student);
			User u1 = userservice.insertStudent(student);

			return new ResponseEntity<>(u1, HttpStatus.CREATED);
		}
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}
		return jwtutil.generateToken(authRequest.getUserName());
	}
}
