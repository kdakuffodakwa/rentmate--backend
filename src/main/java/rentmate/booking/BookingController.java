package rentmate.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Map<String, String> body) {
        Booking booking = bookingService.createBooking(
                UUID.fromString(body.get("renterId")),
                UUID.fromString(body.get("itemId")),
                LocalDate.parse(body.get("startDate")),
                LocalDate.parse(body.get("endDate")),
                body.get("pickupType")
        );
        return ResponseEntity.ok(Map.of(
                "id", booking.getId(),
                "status", booking.getStatus(),
                "totalPrice", booking.getTotalPrice(),
                "startDate", booking.getStartDate().toString(),
                "endDate", booking.getEndDate().toString()
        ));
    }

    @GetMapping("/renter/{renterId}")
    public ResponseEntity<List<Booking>> getByRenter(@PathVariable UUID renterId) {
        return ResponseEntity.ok(bookingService.getBookingsByRenter(renterId));
    }
}