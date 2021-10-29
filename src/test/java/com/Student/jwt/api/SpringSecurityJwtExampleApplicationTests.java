package com.Student.jwt.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.jwt.api.SpringJwtStudentApplication;
import com.jwt.api.entity.Project;
import com.jwt.api.entity.Role;
import com.jwt.api.entity.User;
import com.jwt.api.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@Import(UserServiceImpl.class)
@SpringBootTest(classes = SpringJwtStudentApplication.class)
public class SpringSecurityJwtExampleApplicationTests {

	@Autowired
	private UserServiceImpl service;

	@Test
	public void testGetAllStudent() {
		List<User> foundstudent = service.retriveAllUsers();

		assertNotNull(foundstudent);
		assertEquals(service.retriveAllUsers().size(), foundstudent.size());
	}

	@Test
	public void testAddStudent() {
		User stud = new User();
		List<Project> listofproject = new ArrayList<>();
		Role role = new Role(1, "Student");
		stud.setRole(role);
		Project pro = new Project();
		pro.setDuration(40);
		pro.setProjectId(7);
		pro.setProjectName("abc");
		listofproject.add(pro);
		stud.setProject(listofproject);
		stud.setEmail("bb@gmail1.com");
		stud.setFirstName("bharat1");
		stud.setMobile(898998989);
		stud.setId(7);
		stud.setLastName("chavhan1");

		service.insertStudent(stud);
		Optional<User> s1 = service.findUser(7);
		assertEquals(s1.get().getEmail(), stud.getEmail());
	}

	@Test
	public void testGetStudent() {
		String name = null;
		Optional<User> stud = service.findUser(7);
		if (stud.isPresent()) {
			name = stud.get().getFirstName();
		}
		assertTrue("bharat1".equals(name));
	}

}
