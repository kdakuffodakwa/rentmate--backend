package rentmate.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rentmate.booking.Booking;
import rentmate.booking.BookingRepository;
import rentmate.user.User;
import rentmate.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public Message sendMessage(UUID bookingId, UUID senderId,
                               UUID receiverId, String content) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setBooking(booking);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        return messageRepository.save(message);
    }

    public List<Message> getMessagesByBooking(UUID bookingId) {
        return messageRepository.findByBooking_IdOrderBySentAtAsc(bookingId);
    }

    public List<Message> getUnreadMessages(UUID receiverId) {
        return messageRepository.findByReceiver_IdAndIsReadFalse(receiverId);
    }

    public List<Message> getAllMessagesForUser(UUID userId) {
        return messageRepository.findBySender_IdOrReceiver_IdOrderBySentAtDesc(userId, userId);
    }

    public void markAsRead(UUID bookingId, UUID receiverId) {
        List<Message> unread = messageRepository.findByBooking_IdAndReceiver_IdAndIsReadFalse(bookingId, receiverId);
        for (Message message : unread) {
            message.setIsRead(true);
        }
        messageRepository.saveAll(unread);
    }
}