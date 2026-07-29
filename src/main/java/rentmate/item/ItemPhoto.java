package rentmate.item;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "item_photos")
public class ItemPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"itemPhotos", "owner", "hibernateLazyInitializer"})
    private Item item;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;
}