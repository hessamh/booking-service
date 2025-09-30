package com.example.bookingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_user",
        uniqueConstraints = {
            @UniqueConstraint(name = "uq_user_email", columnNames = "email")
        },
        indexes = {
            @Index(name = "idx_user_email", columnList = "email")
        })
@Getter
@Setter
public class User extends BaseEntity {

    @NotBlank
    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, length = 180)
    private String email;
}
