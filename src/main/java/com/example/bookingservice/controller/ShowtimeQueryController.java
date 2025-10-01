package com.example.bookingservice.controller;

import com.example.bookingservice.dto.SeatAvailabilityResponse;
import com.example.bookingservice.service.ShowtimeQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeQueryController {

    private final ShowtimeQueryService showtimeQueryService;

    public ShowtimeQueryController(ShowtimeQueryService showtimeQueryService) {
        this.showtimeQueryService = showtimeQueryService;
    }

    @GetMapping("/{showtimeId}/seats")
    public List<SeatAvailabilityResponse> seats(@PathVariable Long showtimeId) {
        return showtimeQueryService.getSeatAvailability(showtimeId);
    }
}
