package com.example.bookingservice.repository;

import com.example.bookingservice.entity.BookingItem;
import com.example.bookingservice.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {

    boolean existsByShowtimeIdAndSeatId(Long showtimeId, Long seatId);

    @Query("select bi.seat.id from BookingItem bi " +
            "where bi.showtime.id = :showtimeId and bi.booking.status in :bookingStatuses")
    List<Long> findReservedSeatIds(Long showtimeId, Collection<BookingStatus> bookingStatuses);
}
