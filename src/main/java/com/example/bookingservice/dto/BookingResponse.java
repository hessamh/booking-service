package com.example.bookingservice.dto;

import com.example.bookingservice.entity.BookingStatus;

import java.math.BigDecimal;
import java.util.List;

public class BookingResponse {

    private Long bookingId;
    private BookingStatus status;
    private BigDecimal totalAmount;
    private List<BookingItemResponse> items;

    public BookingResponse(Long bookingId, BookingStatus status, BigDecimal totalAmount, List<BookingItemResponse> items) {
        this.bookingId = bookingId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<BookingItemResponse> getItems() {
        return items;
    }
}
