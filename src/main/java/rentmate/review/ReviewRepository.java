package rentmate.review;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByReviewee_Id(UUID revieweeId);
    List<Review> findByBooking_Id(UUID bookingId);
}