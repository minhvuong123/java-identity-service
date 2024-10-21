package kiwi.com.example.identify_service.service;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import kiwi.com.example.identify_service.dto.request.UserCreationRequest;
import kiwi.com.example.identify_service.dto.response.UserResponse;
import kiwi.com.example.identify_service.entity.User;
import kiwi.com.example.identify_service.exception.AppException;
import kiwi.com.example.identify_service.repsitory.UserRepository;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse response;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);
        request = UserCreationRequest.builder()
                .username("kiwi")
                .firstName("kiwi")
                .lastName("kiwi")
                .password("12345678")
                .dob(dob)
                .build();
        user = User.builder()
                .id("c9a002sa9s923sda")
                .username("kiwi")
                .firstName("kiwi")
                .lastName("kiwi")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest() throws Exception {
        //      log.info("Hello test");
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
                .thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

        // when
        var responseA = userService.createUser(request);

        // then
        Assertions.assertThat(responseA.getId()).isEqualTo("c9a002sa9s923sda");
        Assertions.assertThat(responseA.getUsername()).isEqualTo("kiwi");
    }

//    @Test
//    void createUser_userExisted_fail() throws Exception {
//        // Given
//        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
//                .thenReturn(true);
//
//        // then
//        var exception = org.junit.jupiter.api.Assertions.assertThrows(
//                AppException.class, () -> userService.createUser(request));
//
//        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
//    }

    @Test
    @WithMockUser(username = "kiwi")
    void getMyInfo_valid_success() {
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("kiwi");
        Assertions.assertThat(response.getId()).isEqualTo("c9a002sa9s923sda");
    }

    @Test
    @WithMockUser(username = "kiwi")
    void getMyInfo_userNotFound_error() {
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.ofNullable(null));

        var exception =
                org.junit.jupiter.api.Assertions.assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }
}
