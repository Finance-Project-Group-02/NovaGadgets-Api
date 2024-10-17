package com.Group_02.NovaGadgets_Api.user.service;

import com.Group_02.NovaGadgets_Api.user.dto.request.UserLoginDto;
import com.Group_02.NovaGadgets_Api.user.dto.response.UserReponseDto;
import com.Group_02.NovaGadgets_Api.user.model.UsersEntity;

import java.util.List;


public interface UserService {
    public abstract UsersEntity createUser(UsersEntity user);
    public abstract List<UserReponseDto> getAllUsers();
    public abstract UserReponseDto login(UserLoginDto user);
    public abstract void deleteUser(int id);
    public abstract UserReponseDto getUserById(int id);
}