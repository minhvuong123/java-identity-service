package kiwi.com.example.identify_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kiwi.com.example.identify_service.dto.request.PermissionRequest;
import kiwi.com.example.identify_service.dto.response.PermissionResponse;
import kiwi.com.example.identify_service.entity.Permission;
import kiwi.com.example.identify_service.mapper.PermissionMapper;
import kiwi.com.example.identify_service.repsitory.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();

        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}
