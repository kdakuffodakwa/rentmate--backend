package rentmate.message;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByBooking_IdOrderBySentAtAsc(UUID bookingId);
    List<Message> findByReceiver_IdAndIsReadFalse(UUID receiverId);
    List<Message> findBySender_IdOrReceiver_IdOrderBySentAtDesc(UUID senderId, UUID receiverId);
    List<Message> findByBooking_IdAndReceiver_IdAndIsReadFalse(UUID bookingId, UUID receiverId);
}