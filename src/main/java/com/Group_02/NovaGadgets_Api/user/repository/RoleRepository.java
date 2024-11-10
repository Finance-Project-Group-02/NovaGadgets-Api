package com.Group_02.NovaGadgets_Api.user.repository;

import com.Group_02.NovaGadgets_Api.user.model.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RolesEntity, Integer> {
    Optional<RolesEntity> findByNameRole(String nameRole);
}
