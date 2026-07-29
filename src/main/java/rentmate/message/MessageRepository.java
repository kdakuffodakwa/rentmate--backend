package rentmate.message;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByBooking_IdOrderBySentAtAsc(UUID bookingId);
    List<Message> findByReceiver_IdAndIsReadFalse(UUID receiverId);
}