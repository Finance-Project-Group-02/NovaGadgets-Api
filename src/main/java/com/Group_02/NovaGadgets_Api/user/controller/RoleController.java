package com.Group_02.NovaGadgets_Api.user.controller;

import com.Group_02.NovaGadgets_Api.user.model.RolesEntity;
import com.Group_02.NovaGadgets_Api.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //URL: http://localhost:8080/api/v1/roles
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/roles")
    public ResponseEntity<List<RolesEntity>> getAllRoles() {
        return new ResponseEntity<List<RolesEntity>>(roleService.getAllRoles(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/v1/roles
    //Method: POST
    @Transactional
    @PostMapping("/roles")
    public ResponseEntity<RolesEntity> createRole(@RequestBody RolesEntity role) {
        return new ResponseEntity<RolesEntity>(roleService.createRole(role), HttpStatus.CREATED);
    }
}
