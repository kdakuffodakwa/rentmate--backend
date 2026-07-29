package rentmate.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment(@RequestBody Map<String, String> body) {
        Payment payment = paymentService.initiatePayment(
                UUID.fromString(body.get("bookingId")),
                body.get("momoNumber")
        );
        return ResponseEntity.ok(Map.of(
                "id", payment.getId(),
                "amount", payment.getAmount(),
                "momoTransactionId", payment.getMomoTransactionId(),
                "paymentStatus", payment.getPaymentStatus(),
                "escrowStatus", payment.getEscrowStatus(),
                "paidAt", payment.getPaidAt().toString()
        ));
    }

    @PostMapping("/release/{bookingId}")
    public ResponseEntity<?> releasePayment(@PathVariable UUID bookingId) {
        Payment payment = paymentService.releasePayment(bookingId);
        return ResponseEntity.ok(Map.of(
                "id", payment.getId(),
                "amount", payment.getAmount(),
                "escrowStatus", payment.getEscrowStatus(),
                "paymentStatus", payment.getPaymentStatus(),
                "releasedAt", payment.getReleasedAt().toString()
        ));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getPayment(@PathVariable UUID bookingId) {
        Payment payment = paymentService.getPaymentByBooking(bookingId);
        return ResponseEntity.ok(Map.of(
                "id", payment.getId(),
                "amount", payment.getAmount(),
                "momoTransactionId", payment.getMomoTransactionId(),
                "paymentStatus", payment.getPaymentStatus(),
                "escrowStatus", payment.getEscrowStatus(),
                "paidAt", payment.getPaidAt().toString()
        ));
    }
}