package rentmate.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, String> body) {
        Review review = reviewService.createReview(
                UUID.fromString(body.get("bookingId")),
                UUID.fromString(body.get("reviewerId")),
                UUID.fromString(body.get("revieweeId")),
                Integer.parseInt(body.get("rating")),
                body.get("comment"),
                body.get("reviewType")
        );
        return ResponseEntity.ok(Map.of(
                "id", review.getId(),
                "rating", review.getRating(),
                "comment", review.getComment(),
                "reviewType", review.getReviewType()
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsForUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(reviewService.getReviewsForUser(userId));
    }
}