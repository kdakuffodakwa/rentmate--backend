package rentmate.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rentmate.booking.BookingRepository;
import rentmate.item.Item;
import rentmate.item.ItemRepository;
import rentmate.review.ReviewRepository;
import rentmate.user.User;
import rentmate.user.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long totalUsers = userRepository.count();
        long totalItems = itemRepository.count();
        long totalBookings = bookingRepository.count();
        long availableItems = itemRepository.findByStatus("available").size();
        long rentedItems = itemRepository.findByStatus("rented").size();

        return ResponseEntity.ok(Map.of(
                "totalUsers", totalUsers,
                "totalItems", totalItems,
                "totalBookings", totalBookings,
                "availableItems", availableItems,
                "rentedItems", rentedItems
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok(Map.of("message", "User deleted"));
    }

    @PutMapping("/users/{userId}/verify")
    public ResponseEntity<?> verifyUser(@PathVariable UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsVerified(true);
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User verified"));
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable UUID itemId) {
        itemRepository.deleteById(itemId);
        return ResponseEntity.ok(Map.of("message", "Item deleted"));
    }
}