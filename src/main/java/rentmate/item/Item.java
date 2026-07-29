package rentmate.item;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import rentmate.user.User;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"passwordHash", "ghanaCardUrl", "hibernateLazyInitializer"})
    private User owner;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(name = "daily_price", nullable = false)
    private BigDecimal dailyPrice;

    @Column(name = "weekly_price")
    private BigDecimal weeklyPrice;

    @Column(name = "damage_deposit")
    private BigDecimal damageDeposit = BigDecimal.ZERO;

    @Column(nullable = false, length = 255)
    private String location;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Column(nullable = false, length = 20)
    private String status = "available";
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"item"})
    private java.util.List<rentmate.item.ItemPhoto> itemPhotos = new java.util.ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

}