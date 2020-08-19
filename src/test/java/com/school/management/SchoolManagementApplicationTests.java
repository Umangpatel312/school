package com.school.management;

import com.school.management.restResource.UserLoginResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SchoolManagementApplication.class)
class SchoolManagementApplicationTests {

	@Autowired
	private UserLoginResource userLoginResource;
	
	@Test
	void contextLoads() {
		assertThat(userLoginResource).isNotNull();
	}

}
