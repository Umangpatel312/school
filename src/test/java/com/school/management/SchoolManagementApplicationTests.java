package com.school.management;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.school.management.restResource.UserLoginResource;

@SpringBootTest(classes = SchoolManagementApplication.class)
class SchoolManagementApplicationTests {

  @Autowired
  private UserLoginResource userLoginResource;

  @Test
  void contextLoads() {
    assertThat(userLoginResource).isNotNull();
  }

}
