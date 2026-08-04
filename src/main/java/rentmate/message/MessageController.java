package rentmate.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> body) {
        Message message = messageService.sendMessage(
                UUID.fromString(body.get("bookingId")),
                UUID.fromString(body.get("senderId")),
                UUID.fromString(body.get("receiverId")),
                body.get("content")
        );
        return ResponseEntity.ok(Map.of(
                "id", message.getId(),
                "content", message.getContent(),
                "isRead", message.getIsRead(),
                "sentAt", message.getSentAt().toString()
        ));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Message>> getByBooking(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(messageService.getMessagesByBooking(bookingId));
    }

    @GetMapping("/unread/{receiverId}")
    public ResponseEntity<List<Message>> getUnread(@PathVariable UUID receiverId) {
        return ResponseEntity.ok(messageService.getUnreadMessages(receiverId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Message>> getAllForUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(messageService.getAllMessagesForUser(userId));
    }
}