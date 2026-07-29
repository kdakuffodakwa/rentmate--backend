package rentmate.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rentmate.user.User;
import rentmate.user.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemPhotoRepository itemPhotoRepository;
    private final UserRepository userRepository;

    public Item createItem(UUID ownerId, String title, String description,
                           String category, BigDecimal dailyPrice,
                           BigDecimal weeklyPrice, BigDecimal damageDeposit,
                           String location, List<String> photoUrls) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Item item = new Item();
        item.setOwner(owner);
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);
        item.setDailyPrice(dailyPrice);
        item.setWeeklyPrice(weeklyPrice);
        item.setDamageDeposit(damageDeposit != null ? damageDeposit : BigDecimal.ZERO);
        item.setLocation(location);

        Item savedItem = itemRepository.save(item);

        if (photoUrls != null) {
            for (int i = 0; i < photoUrls.size(); i++) {
                ItemPhoto photo = new ItemPhoto();
                photo.setItem(savedItem);
                photo.setPhotoUrl(photoUrls.get(i));
                photo.setSortOrder(i);
                itemPhotoRepository.save(photo);
            }
        }

        return savedItem;
    }

    public List<Item> getAllItems() {
        return itemRepository.findByStatus("available");
    }

    public List<Item> getItemsByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    public List<Item> getItemsByOwner(UUID ownerId) {
        return itemRepository.findByOwner_Id(ownerId);
    }

    public void deleteItem(UUID itemId) {
        itemRepository.deleteById(itemId);
    }
}