package com.Group_02.NovaGadgets_Api.user.service.impl;

import com.Group_02.NovaGadgets_Api.user.model.RolesEntity;
import com.Group_02.NovaGadgets_Api.user.repository.RoleRepository;
import com.Group_02.NovaGadgets_Api.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RolesEntity createRole(RolesEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public List<RolesEntity> getAllRoles() {
        return roleRepository.findAll();
    }

}