package kiwi.com.example.identify_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import kiwi.com.example.identify_service.dto.request.UserUpdateRequest;
import kiwi.com.example.identify_service.dto.response.UserResponse;
import kiwi.com.example.identify_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //    @Mapping(source = "firstName", target = "firstName")
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
