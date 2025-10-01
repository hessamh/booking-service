package com.example.bookingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seat",
        uniqueConstraints = {
                // هر صندلی در یک سالن باید یکتا باشد
                @UniqueConstraint(name = "uq_seat_hall_row_num", columnNames = {"hall_id", "row_number", "seat_number"})
        },
        indexes = {
                @Index(name = "idx_seat_hall", columnList = "hall_id")
        })
public class Seat extends BaseEntity{

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_seat_hall"))
    private Hall hall;

    @Min(1)
    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @Min(1)
    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
