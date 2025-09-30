package com.example.bookingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie",
        indexes = { @Index(name = "idx_movie_title", columnList = "title") })
@Getter
@Setter
public class Movie extends BaseEntity{

    @NotBlank
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Min(1)
    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;
}
