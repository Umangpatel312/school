package com.school.management.restController.integration.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.management.entity.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
		
	@Test
	void authenticateWorksThroughAllLayor() throws Exception {
		String email = "abcd@gmail.com";
		String password = "1234567";
		User tempUser = new User(email, password, null);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

		mockMvc.perform(builder).andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value(notNullValue(String.class)));
	}

	@Test
	void authenticateFailInvalidPassowrdWorksThroughAllLayor() throws Exception {
		final String email = "abcd@gmail.com";
		final String password = "123456";
		final String msg = "Bad credentials";
		User tempUser = new User(email, password, null);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

		mockMvc.perform(builder).andExpect(status().isBadRequest()).andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.message", is(msg)))
				.andExpect(jsonPath("$.timeStamp").value(notNullValue(Long.class)));
	}

	@Test
	void authenticateFailUserNotFoundWorksThroughAllLayor() throws Exception {
		final String email = "abcde@gmail.com";
		final String password = "1234567";
		final String msg = "Bad credentials";
		User tempUser = new User(email, password, null);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

		mockMvc.perform(builder).andExpect(status().isBadRequest()).andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.message", is(msg)))
				.andExpect(jsonPath("$.timeStamp").value(notNullValue(Long.class)));
	}

	@Test
	void update_authenticateRequired() throws Exception {
		final String email = "abcd@gmail.com";
		final String password = "1234567";
		final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmNkQGdtYWlsLmNvbSIsImV4cCI6MTU5NzY4ODk3OCwiaWF0IjoxNTk3NjUyOTc4fQ.p6aJMVZpYfPeg3mFmWaPNXaig-jqhCf_JmJyKBAsKMQ";
		User tempUser = new User(email, password, null);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/update/abcd@gmail.com")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").header("Authorization", token)
				.content(this.mapper.writeValueAsBytes(tempUser));

		mockMvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void update_ThrowsuserNotFound_authenticateRequired() throws Exception {
		final String email = "abcd@gmail.com";
		final String password = "1234567";
		final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmNkQGdtYWlsLmNvbSIsImV4cCI6MTU5NzY4ODk3OCwiaWF0IjoxNTk3NjUyOTc4fQ.p6aJMVZpYfPeg3mFmWaPNXaig-jqhCf_JmJyKBAsKMQ";
		final String msg = "Bad credentials";
		User tempUser = new User(email, password, null);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/update/abc@gmail.com")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").header("Authorization", token)
				.content(this.mapper.writeValueAsBytes(tempUser));

		mockMvc.perform(builder).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is(404)))
				.andExpect(jsonPath("$.message", is(msg)))
				.andExpect(jsonPath("$.timeStamp").value(notNullValue(Long.class)));
	}

	@Test
	void update_unauthorized_authenticateRequired() throws Exception {
		final String email = "abcd@gmail.com";
		final String password = "1234567";
		User tempUser = new User(email, password, null);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/update/abc@gmail.com")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(this.mapper.writeValueAsBytes(tempUser));

		mockMvc.perform(builder).andExpect(status().isForbidden());
	}

	@Test
	void create_unauthorized_authenticateRequired() throws Exception {
		final String email = "jay@gmail.com";
		final String password = "1234567";
		User tempUser = new User(email, password, null);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(this.mapper.writeValueAsBytes(tempUser));

		mockMvc.perform(builder).andExpect(status().isForbidden());
	}
	
	@Test
	void create_successfullyAdded_authenticateRequired() throws Exception {
		final String email = "jay@gmail.com";
		final String password = "12345";
		final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmNkQGdtYWlsLmNvbSIsImV4cCI6MTU5NzY4ODk3OCwiaWF0IjoxNTk3NjUyOTc4fQ.p6aJMVZpYfPeg3mFmWaPNXaig-jqhCf_JmJyKBAsKMQ";
		User tempUser = new User(email, password, null);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.characterEncoding("UTF-8").header("Authorization", token)
				.content(this.mapper.writeValueAsBytes(tempUser));

		mockMvc.perform(builder).andExpect(status().isOk())
		.andExpect(jsonPath("$.email", is(email)))
		.andExpect(jsonPath("$.password",is(password)));
	}
	
}
