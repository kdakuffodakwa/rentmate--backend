package rentmate.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByRenter_Id(UUID renterId);
    List<Booking> findByItem_Id(UUID itemId);
    List<Booking> findByStatus(String status);
}