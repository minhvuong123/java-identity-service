package kiwi.com.example.identify_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import kiwi.com.example.identify_service.dto.request.RoleRequest;
import kiwi.com.example.identify_service.dto.response.RoleResponse;
import kiwi.com.example.identify_service.mapper.RoleMapper;
import kiwi.com.example.identify_service.repsitory.PermissionRepository;
import kiwi.com.example.identify_service.repsitory.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();

        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}