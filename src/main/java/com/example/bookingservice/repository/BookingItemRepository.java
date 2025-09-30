package com.example.bookingservice.repository;

import com.example.bookingservice.entity.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {

    boolean existsByShowtimeIdAndSeatId(Long showtimeId, Long seatId);
}
