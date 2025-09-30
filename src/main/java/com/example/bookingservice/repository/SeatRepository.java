package com.example.bookingservice.repository;

import com.example.bookingservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByHallIdOrderByRowNumberAscSeatNumberAsc(Long hallId);
}
