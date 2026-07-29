package rentmate.payment;

import jakarta.persistence.*;
import lombok.Data;
import rentmate.booking.Booking;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Booking booking;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "momo_transaction_id")
    private String momoTransactionId;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus = "pending";

    @Column(name = "escrow_status", nullable = false, length = 20)
    private String escrowStatus = "held";

    @Column(name = "paid_at")
    private OffsetDateTime paidAt;

    @Column(name = "released_at")
    private OffsetDateTime releasedAt;
}