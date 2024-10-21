package kiwi.com.example.identify_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kiwi.com.example.identify_service.dto.request.RoleRequest;
import kiwi.com.example.identify_service.dto.response.RoleResponse;
import kiwi.com.example.identify_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
