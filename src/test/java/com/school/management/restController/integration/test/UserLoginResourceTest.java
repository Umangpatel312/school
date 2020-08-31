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
import com.school.management.dto.UserDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserLoginResourceTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  void authenticateWorksThroughAllLayor() throws Exception {
    String email = "abc@gmail.com";
    String password = "12345";
    UserDTO tempUser = new UserDTO(email, password);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

    mockMvc.perform(builder).andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(notNullValue(String.class)));
  }

  @Test
  void authenticateFailInvalidPassowrdWorksThroughAllLayor() throws Exception {
    final String email = "abc@gmail.com";
    final String password = "123456";
    final String msg = "Bad credentials";
    UserDTO tempUser = new UserDTO(email, password);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

    mockMvc.perform(builder).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is(400))).andExpect(jsonPath("$.message", is(msg)))
        .andExpect(jsonPath("$.timeStamp").value(notNullValue(Long.class)));
  }

  @Test
  void authenticateFailUserNotFoundWorksThroughAllLayor() throws Exception {
    final String email = "abcde@gmail.com";
    final String password = "12345";
    final String msg = "Bad credentials";
    UserDTO tempUser = new UserDTO(email, password);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

    mockMvc.perform(builder).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is(400))).andExpect(jsonPath("$.message", is(msg)))
        .andExpect(jsonPath("$.timeStamp").value(notNullValue(Long.class)));
  }

  @Test
  void update_success() throws Exception {
    final String email = "abc@gmail.com";
    final String password = "12345";

    UserDTO tempUser = new UserDTO(email, password);
    tempUser.setRole(2);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/update/1")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  void update_ThrowsuserNotFoundWhoIsAdding() throws Exception {
    final String email = "abc@gmail.com";
    final String password = "12345";
    final String msg = "Bad credentials";
    UserDTO tempUser = new UserDTO(email, password);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/update/10")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

    mockMvc.perform(builder).andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status", is(404))).andExpect(jsonPath("$.message", is(msg)))
        .andExpect(jsonPath("$.timeStamp").value(notNullValue(Long.class)));
  }

  @Test
  void create_successfullyAdded() throws Exception {
    final String email = "umang@gmail.com";
    final String password = "12345";
    final int role = 3;
    UserDTO tempUser = new UserDTO(email, password);
    tempUser.setRole(role);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/create")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").characterEncoding("UTF-8")
        .content(this.mapper.writeValueAsBytes(tempUser)).requestAttr("username", "abc@gmail.com");

    mockMvc.perform(builder).andExpect(status().isCreated())
        .andExpect(jsonPath("$.email", is(email))).andExpect(jsonPath("$.password", is(password)))
        .andExpect(jsonPath("$.role", is(3)));
  }

  @Test
  void getUsersByAddedHimParticularRole_success() throws Exception {

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/getUserByParticularRole/3")
        .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").characterEncoding("UTF-8")
        .requestAttr("username", "abc@gmail.com");

    mockMvc.perform(builder).andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(2)));
  }

}
