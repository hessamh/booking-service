package com.example.bookingservice.dto;

import java.math.BigDecimal;

public class BookingItemResponse {

    private Long seatId;
    private int rowNumber;
    private int seatNumber;
    private BigDecimal price;

    public BookingItemResponse(Long seatId, int rowNumber, int seatNumber, BigDecimal price) {
        this.seatId = seatId;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public Long getSeatId() {
        return seatId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
