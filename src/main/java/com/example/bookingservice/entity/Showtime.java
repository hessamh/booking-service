package com.example.bookingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "showtime",
        uniqueConstraints = {
                // برای سادگی: یک سالن در یک زمان شروع مشخص فقط یک سانس دارد
                @UniqueConstraint(name = "uq_showtime_hall_start", columnNames = {"hall_id", "start_at"})
        },
        indexes = {
                @Index(name = "idx_showtime_movie", columnList = "movie_id"),
                @Index(name = "idx_showtime_hall", columnList = "hall_id"),
                @Index(name = "idx_showtime_start", columnList = "start_at")
        })
@Getter
@Setter
public class Showtime extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_showtime_movie"))
    private Movie movie;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_showtime_hall"))
    private Hall hall;

    @NotNull
    @Column(name = "start_at", nullable = false)
    private OffsetDateTime startAt;

    @NotNull
    @Column(name = "end_at", nullable = false)
    private OffsetDateTime endAt;

    // قیمت پایه بلیت برای این سانس
    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice = BigDecimal.ZERO;
}
