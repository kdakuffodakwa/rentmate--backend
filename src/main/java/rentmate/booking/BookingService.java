package rentmate.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rentmate.item.Item;
import rentmate.item.ItemRepository;
import rentmate.user.User;
import rentmate.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Booking createBooking(UUID renterId, UUID itemId,
                                 LocalDate startDate, LocalDate endDate,
                                 String pickupType) {

        User renter = userRepository.findById(renterId)
                .orElseThrow(() -> new RuntimeException("Renter not found"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getStatus().equals("available")) {
            throw new RuntimeException("Item is not available");
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        BigDecimal totalPrice = item.getDailyPrice().multiply(BigDecimal.valueOf(days));

        Booking booking = new Booking();
        booking.setRenter(renter);
        booking.setItem(item);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setTotalPrice(totalPrice);
        booking.setDepositAmount(item.getDamageDeposit());
        booking.setPickupType(pickupType != null ? pickupType : "pickup");

        item.setStatus("rented");
        itemRepository.save(item);

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByRenter(UUID renterId) {
        return bookingRepository.findByRenter_Id(renterId);
    }
}