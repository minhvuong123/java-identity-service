package kiwi.com.example.identify_service.mapper;

import org.mapstruct.Mapper;

import kiwi.com.example.identify_service.dto.request.PermissionRequest;
import kiwi.com.example.identify_service.dto.response.PermissionResponse;
import kiwi.com.example.identify_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
