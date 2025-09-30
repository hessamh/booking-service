package com.example.bookingservice.entity;

public enum BookingStatus {

    PENDING,     // ساخته شده ولی پرداخت/تایید نشده
    CONFIRMED,   // پرداخت/تایید شده
    CANCELED,    // لغو شده
    EXPIRED      // منقضی (در فاز بعدی اگر TTL بگذاریم)
}
