package rentmate.item;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByCategory(String category);
    List<Item> findByStatus(String status);
    List<Item> findByOwner_Id(UUID ownerId);
}