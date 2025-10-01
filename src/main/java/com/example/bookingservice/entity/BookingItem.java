package com.example.bookingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_item",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_booking_item_showtime_seat", columnNames = {"showtime_id", "seat_id"})
        },
        indexes = {
                @Index(name = "idx_booking_item_booking", columnList = "booking_id"),
                @Index(name = "idx_booking_item_showtime", columnList = "showtime_id"),
                @Index(name = "idx_booking_item_seat", columnList = "seat_id")
        })
public class BookingItem extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_item_booking"))
    private Booking booking;

    // برای enforce کردن UniqueConstraint روی (showtime, seat)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_item_showtime"))
    private Showtime showtime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_item_seat"))
    private Seat seat;

    @NotNull
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
