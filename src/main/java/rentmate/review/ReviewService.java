package rentmate.review;

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
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public Review createReview(UUID bookingId, UUID reviewerId,
                               UUID revieweeId, int rating,
                               String comment, String reviewType) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        User reviewee = userRepository.findById(revieweeId)
                .orElseThrow(() -> new RuntimeException("Reviewee not found"));

        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.setBooking(booking);
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewType(reviewType);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForUser(UUID userId) {
        return reviewRepository.findByReviewee_Id(userId);
    }
}