package kiwi.com.example.identify_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kiwi.com.example.identify_service.dto.request.UserCreationRequest;
import kiwi.com.example.identify_service.dto.request.UserUpdateRequest;
import kiwi.com.example.identify_service.dto.response.UserResponse;
import kiwi.com.example.identify_service.entity.User;
import kiwi.com.example.identify_service.enums.Role;
import kiwi.com.example.identify_service.exception.AppException;
import kiwi.com.example.identify_service.exception.ErrorCode;
import kiwi.com.example.identify_service.mapper.UserMapper;
import kiwi.com.example.identify_service.repsitory.RoleRepository;
import kiwi.com.example.identify_service.repsitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    RoleRepository roleRepository;

    public User createUser(UserCreationRequest request) {

//        if (userRepository.existsByUsername(request.getUsername())) {
//            //            throw new RuntimeException("User existed!");
//            //            throw new RuntimeException(ErrorCode.UNCATEORIZED_EXCEPTION.getMessage());
//            throw new AppException(ErrorCode.USER_EXISTED);
//        }

        User user = new User();

        user.setUsername(request.getUsername());
        //        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        //        user.setRoles(roles);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User result;
        try { // catch exception for Unique key
            result = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return result;
    }

    @PreAuthorize("hasRole('ADMIN')") // method authorize -> run before executing method
    //    @PreAuthorize("hasAuthority('APPROVE_POST')") token -> scope : "ADMIN CREATE_DATA APPROVE_POST"
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.username == authentication.name") // run after executing method
    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        userMapper.updateUser(user, request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
}
