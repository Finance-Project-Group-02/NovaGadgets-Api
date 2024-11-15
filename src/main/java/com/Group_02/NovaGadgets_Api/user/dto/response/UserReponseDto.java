package com.Group_02.NovaGadgets_Api.user.dto.response;

import com.Group_02.NovaGadgets_Api.user.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReponseDto {

    private int id;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String username;

    private LocalDate birthday;

    private String phoneNumber;

    private String dni;

    private Character gender;

    private String ruc;

    private String currencyType;

    private List<RoleDTO> roles;
}
