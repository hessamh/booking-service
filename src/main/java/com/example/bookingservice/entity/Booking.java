package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking",
        indexes = {
                @Index(name = "idx_booking_user", columnList = "user_id"),
                @Index(name = "idx_booking_showtime", columnList = "showtime_id"),
                @Index(name = "idx_booking_status", columnList = "status")
        })
@Getter
@Setter
public class Booking extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_user"))
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_showtime"))
    private Showtime showtime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    // برای کنترل خوش‌بینانه رقابت روی رکورد رزرو
    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingItem> items = new ArrayList<>();
}
