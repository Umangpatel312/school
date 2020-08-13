package com.school.management.restController;


import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.management.service.UserLoginService;

@WebMvcTest(controllers = UserLoginResource.class)
@ActiveProfiles("test")
public class LoginResourcesTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	UserLoginService userLoginService;
		
	@Test
	public void post_authenticate_user_returnJwt() throws Exception{
		
		String email="abc@gmail.com";
		String password="123456";
		
		com.school.management.entity.User tempUser=
				new com.school.management.entity.User(email,password,null);
		
		Mockito.when(userLoginService.loadUserByUsername(Mockito.anyString()))
			.thenReturn(new User(email, password, emptyList()));
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));
		String jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmNAZ21haWwuY29tIiwiZXhwIjoxNTk3MjY3OTgyLCJpYXQiOjE1OTcyMzE5ODJ9.XynUqR0QcnJ9liME-Ct2LyBWqZHYfv77HBRHMwXYmFM";
		
		mockMvc.perform(builder)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.jwttoken", is(jwtToken)))
			.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(tempUser)));
		
	}
}
