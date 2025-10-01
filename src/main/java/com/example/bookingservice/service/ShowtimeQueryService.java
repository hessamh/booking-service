package com.example.bookingservice.service;

import com.example.bookingservice.common.NotFoundException;
import com.example.bookingservice.dto.SeatAvailabilityResponse;
import com.example.bookingservice.entity.BookingStatus;
import com.example.bookingservice.entity.Seat;
import com.example.bookingservice.entity.Showtime;
import com.example.bookingservice.repository.BookingItemRepository;
import com.example.bookingservice.repository.SeatRepository;
import com.example.bookingservice.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class ShowtimeQueryService {

    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final BookingItemRepository bookingItemRepository;

    public ShowtimeQueryService(ShowtimeRepository showtimeRepository,
                                SeatRepository seatRepository,
                                BookingItemRepository bookingItemRepository) {
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.bookingItemRepository = bookingItemRepository;
    }

    @Transactional(readOnly = true)
    public List<SeatAvailabilityResponse> getSeatAvailability(Long showtimeId) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new NotFoundException("Showtime not found: " + showtimeId));

        Long hallId = showtime.getHall().getId();
        List<Seat> seats = seatRepository.findByHallIdOrderByRowNumberAscSeatNumberAsc(hallId);

        // رزرو شده = PENDING یا CONFIRMED
        var reservedSeatIds = new HashSet<>(bookingItemRepository.findReservedSeatIds(
                showtimeId, Arrays.asList(BookingStatus.PENDING, BookingStatus.CONFIRMED)));

        List<SeatAvailabilityResponse> result = new ArrayList<>(seats.size());
        for (Seat s : seats) {
            boolean available = !reservedSeatIds.contains(s.getId());
            result.add(new SeatAvailabilityResponse(s.getId(), s.getRowNumber(), s.getSeatNumber(), available));
        }
        return result;
    }
}
