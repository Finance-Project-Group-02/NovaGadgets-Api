package com.Group_02.NovaGadgets_Api.user.service.impl;


import com.Group_02.NovaGadgets_Api.shared.config.ModelMapperConfig;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import com.Group_02.NovaGadgets_Api.shared.exception.ValidationException;
import com.Group_02.NovaGadgets_Api.user.dto.request.UserLoginDto;
import com.Group_02.NovaGadgets_Api.user.dto.response.UserReponseDto;
import com.Group_02.NovaGadgets_Api.user.model.RolesEntity;
import com.Group_02.NovaGadgets_Api.user.model.UsersEntity;
import com.Group_02.NovaGadgets_Api.user.repository.RoleRepository;
import com.Group_02.NovaGadgets_Api.user.repository.UserRepository;
import com.Group_02.NovaGadgets_Api.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsersEntity createUser(UsersEntity user) {
        List<RolesEntity> managedRoles = new ArrayList<>();

        for (RolesEntity role : user.getRoles()) {
            RolesEntity managedRole = roleRepository.findById(role.getId()).orElseThrow(() ->
                    new ResourceNotFoundException("Role not found with id: " + role.getId())
            );
            managedRoles.add(managedRole);
        }

        user.setRoles(managedRoles);

        validateUser(user);

        return userRepository.save(user);
    }

    @Override
    public List<UserReponseDto> getAllUsers() {

        List<UsersEntity> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserReponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserReponseDto login(UserLoginDto user) {

        if (!userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceNotFoundException("User not found with email: " + user.getEmail());
        }
        if (!userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
            throw new ValidationException("Your password is incorrect, please try again");
        }

        UsersEntity userEntity = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        return modelMapper.map(userEntity, UserReponseDto.class);
    }

    @Override
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserReponseDto getUserById(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        UsersEntity userEntity = userRepository.findById(id);
        return modelMapper.map(userEntity, UserReponseDto.class);
    }

    private void validateUser(UsersEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        if (userRepository.existsByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber())) {
            throw new ValidationException("Email or phone number already exists");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new ValidationException("User must have at least one role");
        }

        for (RolesEntity role : user.getRoles()) {
            if (!role.getNameRole().equals("CLIENT") && !role.getNameRole().equals("ADMIN")) {
                throw new ValidationException("Invalid role");
            }
        }

    }
}