package kiwi.com.example.identify_service.controller;

//import java.time.LocalDate;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//import kiwi.com.example.identify_service.dto.request.UserCreationRequest;
//import kiwi.com.example.identify_service.dto.response.UserResponse;
//import kiwi.com.example.identify_service.entity.User;
//import lombok.extern.slf4j.Slf4j;

//@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//@Testcontainers
public class UserControllerIntegrationTest {
//    @Container
//    static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");
//
//    @DynamicPropertySource
//    static void configureDatasource(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
//        registry.add("spring.datasource.username", () -> MY_SQL_CONTAINER.getUsername());
//        registry.add("spring.datasource.password", () -> MY_SQL_CONTAINER.getPassword());
//        registry.add("spring.datasource.driverClassName", () -> "com.mysql.cj.jdbc.Driver");
//        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
//    }
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private UserCreationRequest request;
//    private UserResponse response;
//    private User user;
//    private LocalDate dob;

//    @BeforeEach
//    void initData() {
//        dob = LocalDate.of(1990, 1, 1);
//        request = UserCreationRequest.builder()
//                .username("kiwi")
//                .firstName("kiwi")
//                .lastName("kiwi")
//                .password("12345678")
//                .dob(dob)
//                .build();
//        user = User.builder()
//                .id("c9a002sa9s923sda")
//                .username("kiwi")
//                .firstName("kiwi")
//                .lastName("kiwi")
//                .dob(dob)
//                .build();
//    }

//    @Test
//    void createUser_validRequest() throws Exception {
//        //      log.info("Hello test");
//        // Given
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String content = objectMapper.writeValueAsString(request);
//
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
//        //                .andExpect(MockMvcResultMatchers.jsonPath("result.code").value("111"));
//    }
}
