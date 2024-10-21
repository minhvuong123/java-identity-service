package kiwi.com.example.identify_service.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kiwi.com.example.identify_service.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
