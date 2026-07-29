package rentmate.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rentmate.booking.Booking;
import rentmate.booking.BookingRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public Payment initiatePayment(UUID bookingId, String momoNumber) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (paymentRepository.findByBooking_Id(bookingId).isPresent()) {
            throw new RuntimeException("Payment already exists for this booking");
        }

        // Simulate MoMo transaction ID
        String simulatedTxId = "MOMO-" + System.currentTimeMillis();

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getTotalPrice());
        payment.setMomoTransactionId(simulatedTxId);
        payment.setPaymentStatus("paid");
        payment.setEscrowStatus("held");
        payment.setPaidAt(OffsetDateTime.now());

        // Update booking status to confirmed
        booking.setStatus("confirmed");
        bookingRepository.save(booking);

        return paymentRepository.save(payment);
    }

    public Payment releasePayment(UUID bookingId) {
        Payment payment = paymentRepository.findByBooking_Id(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setEscrowStatus("released");
        payment.setReleasedAt(OffsetDateTime.now());
        payment.setPaymentStatus("paid");

        // Update booking status to completed
        Booking booking = payment.getBooking();
        booking.setStatus("completed");
        bookingRepository.save(booking);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByBooking(UUID bookingId) {
        return paymentRepository.findByBooking_Id(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}