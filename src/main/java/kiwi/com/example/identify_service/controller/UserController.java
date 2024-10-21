package kiwi.com.example.identify_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import kiwi.com.example.identify_service.dto.request.ApiResponse;
import kiwi.com.example.identify_service.dto.request.UserCreationRequest;
import kiwi.com.example.identify_service.dto.request.UserUpdateRequest;
import kiwi.com.example.identify_service.dto.response.UserResponse;
import kiwi.com.example.identify_service.entity.User;
import kiwi.com.example.identify_service.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        //        ApiResponse<User> apiResponse = new ApiResponse<>();
        //
        //        apiResponse.setResult(userService.createUser(request));

        ApiResponse<User> apiResponse = ApiResponse.<User>builder()
                .result(userService.createUser(request))
                .build();

        return apiResponse;
    }

    @GetMapping
    List<User> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(g -> log.info(g.getAuthority())); // SCOPE_ADMIN

        return userService.getUsers();
    }

    @GetMapping("/myInfo")
    UserResponse getMyInfo() {
        return userService.getMyInfo();
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "User has been deleted!";
    }
}
