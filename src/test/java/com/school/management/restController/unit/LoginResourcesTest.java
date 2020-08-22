package com.school.management.restController.unit;


import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.management.restResource.UserLoginResource;
import com.school.management.restResource.UserNotFoundException;
import com.school.management.service.UserLoginService;
import com.school.management.util.JwtUtil;


@WebMvcTest(controllers = UserLoginResource.class)
@AutoConfigureMockMvc
public class LoginResourcesTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper mapper;

  @MockBean
  private UserLoginService userLoginService;

  @MockBean
  private JwtUtil jwtUtil;

  @Test
  public void post_authenticate_user_returnJwt() throws Exception {

    String email = "abc@gmail.com";
    String password = "123456";
    final String jwtToken =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmNAZ21haWwuY29tIiwiZXhwIjoxNTk3MjY3OTgyLCJpYXQiOjE1OTcyMzE5ODJ9.XynUqR0QcnJ9liME-Ct2LyBWqZHYfv77HBRHMwXYmFM";

    com.school.management.entity.User tempUser =
        new com.school.management.entity.User(email, password, null);

    Mockito.when(userLoginService.loadUserByUsername(Mockito.anyString()))
        .thenReturn(new User(email, password, emptyList()));

    Mockito.when(jwtUtil.generateToken(Mockito.anyString())).thenReturn(jwtToken);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));


    mockMvc.perform(builder).andExpect(status().isOk())
        .andExpect(jsonPath("$.token", is(jwtToken)));
  }

  @Test
  public void post_authenticate_user_returnNotValid() throws Exception {

    String email = "abc@gmail.com";
    String password = "123456";
    // final String
    // jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmNAZ21haWwuY29tIiwiZXhwIjoxNTk3MjY3OTgyLCJpYXQiOjE1OTcyMzE5ODJ9.XynUqR0QcnJ9liME-Ct2LyBWqZHYfv77HBRHMwXYmFM";
    String msg = "user not found";
    com.school.management.entity.User tempUser =
        new com.school.management.entity.User(email, password, null);

    Mockito.when(userLoginService.loadUserByUsername(Mockito.anyString()))
        .thenThrow(new UserNotFoundException(msg));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/authenticate")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(tempUser));

    mockMvc.perform(builder).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(msg)))
        .andExpect(jsonPath("$.timeStamp").value(notNullValue(Long.class)));
  }

}


