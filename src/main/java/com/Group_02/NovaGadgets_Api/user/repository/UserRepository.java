package com.Group_02.NovaGadgets_Api.user.repository;

import com.Group_02.NovaGadgets_Api.user.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    UsersEntity findByEmailAndPassword(String email, String password);
    UsersEntity findById(int id);
    Boolean existsByEmail(String email);
    Boolean existsByEmailAndPassword(String email, String password);
    Boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
}