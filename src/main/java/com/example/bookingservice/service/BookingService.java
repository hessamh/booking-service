package com.example.bookingservice.service;

import com.example.bookingservice.common.ConflictException;
import com.example.bookingservice.common.NotFoundException;
import com.example.bookingservice.dto.BookingItemResponse;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.CreateBookingRequest;
import com.example.bookingservice.entity.*;
import com.example.bookingservice.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;
    private final BookingItemRepository bookingItemRepository;
    private final EntityManager entityManager;

    public BookingService(UserRepository userRepository,
                          ShowtimeRepository showtimeRepository,
                          SeatRepository seatRepository,
                          BookingRepository bookingRepository,
                          BookingItemRepository bookingItemRepository,
                          EntityManager entityManager) {
        this.userRepository = userRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
        this.bookingItemRepository = bookingItemRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public BookingResponse createBooking(CreateBookingRequest req) {
        // 1) اعتبارسنجی موجودیت‌ها
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + req.getUserId()));

        Showtime showtime = showtimeRepository.findById(req.getShowtimeId())
                .orElseThrow(() -> new NotFoundException("Showtime not found: " + req.getShowtimeId()));

        // 2) صندلی‌های سالنِ این سانس
        Long hallId = showtime.getHall().getId();
        Map<Long, Seat> hallSeats = seatRepository.findByHallIdOrderByRowNumberAscSeatNumberAsc(hallId)
                .stream().collect(Collectors.toMap(Seat::getId, s -> s));

        // 3) بررسی درخواست صندلی‌ها (وجود/تعلق به سالن)
        List<Long> requestedSeatIds = new ArrayList<>(new LinkedHashSet<>(req.getSeatIds())); // حذف تکراری‌ها ولی حفظ ترتیب
        if (requestedSeatIds.isEmpty()) {
            throw new ConflictException("No seats requested");
        }
        for (Long seatId : requestedSeatIds) {
            if (!((Map<?, ?>) hallSeats).containsKey(seatId)) {
                throw new ConflictException("Seat " + seatId + " does not belong to showtime hall");
            }
        }

        // 4) پیش‌چک تداخل رزرو
        for (Long seatId : requestedSeatIds) {
            if (bookingItemRepository.existsByShowtimeIdAndSeatId(showtime.getId(), seatId)) {
                throw new ConflictException("Seat already reserved: " + seatId);
            }
        }

        // 5) ساخت رزرو + آیتم‌ها
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setStatus(BookingStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;
        List<BookingItem> items = new ArrayList<>(requestedSeatIds.size());
        for (Long seatId : requestedSeatIds) {
            Seat seat = hallSeats.get(seatId);
            BookingItem bi = new BookingItem();
            bi.setBooking(booking);
            bi.setShowtime(showtime);
            bi.setSeat(seat);
            bi.setPrice(showtime.getBasePrice()); // فعلاً قیمت پایه؛ می‌تونیم Ruleهای قیمت‌گذاری اضافه کنیم
            items.add(bi);
            total = total.add(bi.getPrice());
        }
        booking.setItems(items);
        booking.setTotalAmount(total);

        // 6) ذخیره و فلش برای فعال شدن UniqueConstraint در همان تراکنش
        try {
            bookingRepository.save(booking);
            entityManager.flush(); // اطمینان از اعمال constraint همین‌جا
        } catch (DataIntegrityViolationException ex) {
            // مسابقهٔ رزرو هم‌زمان روی یک صندلی
            throw new ConflictException("One or more seats were just reserved by another user. Please retry.");
        }

        // 7) پاسخ
        List<BookingItemResponse> itemDtos = booking.getItems().stream()
                .map(bi -> new BookingItemResponse(
                        bi.getSeat().getId(),
                        bi.getSeat().getRowNumber(),
                        bi.getSeat().getSeatNumber(),
                        bi.getPrice()))
                .toList();

        return new BookingResponse(booking.getId(), booking.getStatus(), booking.getTotalAmount(), itemDtos);
    }
}
