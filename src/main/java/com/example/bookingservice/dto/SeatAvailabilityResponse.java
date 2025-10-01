package com.example.bookingservice.dto;

public class SeatAvailabilityResponse {

    private Long seatId;
    private int rowNumber;
    private int seatNumber;
    private boolean available;

    public SeatAvailabilityResponse(Long seatId, int rowNumber, int seatNumber, boolean available) {
        this.seatId = seatId;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.available = available;
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

    public boolean isAvailable() {
        return available;
    }
}
