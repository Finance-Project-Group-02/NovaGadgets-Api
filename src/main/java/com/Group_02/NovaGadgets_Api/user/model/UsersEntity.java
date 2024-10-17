package com.Group_02.NovaGadgets_Api.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Entity
@Table(name = "users")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 40, message = "First name must be between 2 and 40 characters")
    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 40, message = "Last name must be between 2 and 40 characters")
    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 50, message = "Address must be between 5 and 50 characters")
    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 40, message = "Email must be between 5 and 40 characters")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @NotNull(message = "Birthday is mandatory")
    @Past(message = "Birthday must be a date in the past")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{9}", message = "Phone number must be exactly 9 digits")
    @Column(name = "phone_number", nullable = false, length = 9, columnDefinition = "CHAR(9)")
    private String phoneNumber;

    @NotBlank(message = "DNI is required")
    @Pattern(regexp = "\\d{8}", message = "DNI must be exactly 8 digits")
    @Column(name = "dni", nullable = false, length = 8, columnDefinition = "CHAR(8)")
    private String dni;

    @NotNull(message = "Gender is required")
    @Column(name = "gender", nullable = false)
    private Character gender;

    @NotBlank(message = "RUC is required")
    @Pattern(regexp = "\\d{11}", message = "RUC must be exactly 11 digits")
    @Column(name = "ruc", nullable = false , length = 11, columnDefinition = "CHAR(11)")
    private String ruc;

    @NotBlank(message = "Currency type is required")
    @Size(min = 3, max = 3, message = "Currency type must be 3 characters")
    @Column(name = "currency_type", nullable = false, length =  3, columnDefinition = "CHAR(3)")
    private String currencyType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(
            name = "user_by_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<RolesEntity> roles;
}
