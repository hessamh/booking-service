package com.example.bookingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hall",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_hall_name", columnNames = "name")
        })
@Getter
@Setter
public class Hall extends BaseEntity{

    @NotBlank
    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
