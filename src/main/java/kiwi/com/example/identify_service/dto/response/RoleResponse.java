package kiwi.com.example.identify_service.dto.response;

import java.util.Set;

import kiwi.com.example.identify_service.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String name;
    String description;
    Set<Permission> permissions;
}
