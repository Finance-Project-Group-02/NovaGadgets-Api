package com.Group_02.NovaGadgets_Api.user.service;

import com.Group_02.NovaGadgets_Api.user.model.RolesEntity;

import java.util.List;

public interface RoleService {
    public abstract RolesEntity createRole(RolesEntity role);
    public abstract List<RolesEntity> getAllRoles();
}
