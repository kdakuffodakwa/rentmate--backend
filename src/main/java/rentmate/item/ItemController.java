package rentmate.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Map<String, Object> body) {
        List<String> photoUrls = body.get("photoUrls") != null
                ? (List<String>) body.get("photoUrls")
                : null;

        Item item = itemService.createItem(
                UUID.fromString((String) body.get("ownerId")),
                (String) body.get("title"),
                (String) body.get("description"),
                (String) body.get("category"),
                new BigDecimal((String) body.get("dailyPrice")),
                body.get("weeklyPrice") != null ? new BigDecimal((String) body.get("weeklyPrice")) : null,
                body.get("damageDeposit") != null ? new BigDecimal((String) body.get("damageDeposit")) : null,
                (String) body.get("location"),
                photoUrls
        );
        return ResponseEntity.ok(Map.of(
                "id", item.getId(),
                "title", item.getTitle(),
                "category", item.getCategory(),
                "dailyPrice", item.getDailyPrice(),
                "status", item.getStatus()
        ));
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Item>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(itemService.getItemsByCategory(category));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Item>> getByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(itemService.getItemsByOwner(ownerId));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable UUID itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok(Map.of("message", "Item deleted successfully"));
    }
}