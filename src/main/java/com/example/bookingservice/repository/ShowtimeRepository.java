package com.example.bookingservice.repository;

import com.example.bookingservice.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    List<Showtime> findByMovieIdAndStartAtAfterOrderByStartAtAsc(Long movieId, OffsetDateTime after);

}
