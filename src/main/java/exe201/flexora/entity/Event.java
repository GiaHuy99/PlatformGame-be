package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "events", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CommunityId", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OrganizerId", nullable = false)
    private User organizer;

    @Lob
    @Column(name = "Title", nullable = false)
    private String title;

    @Lob
    @Column(name = "Description")
    private String description;

    @Lob
    @Column(name = "Mode")
    private String mode;

    @Lob
    @Column(name = "Location")
    private String location;

    @Column(name = "StartsAt", nullable = false)
    private Instant startsAt;

    @Column(name = "EndsAt", nullable = false)
    private Instant endsAt;

    @ColumnDefault("0")
    @Column(name = "PriceCents", nullable = false)
    private Long priceCents;

    @Column(name = "Capacity")
    private Integer capacity;

    @ColumnDefault("0")
    @Column(name = "EscrowMinCents", nullable = false)
    private Long escrowMinCents;

    @ColumnDefault("0.0000")
    @Column(name = "PlatformFeeRate", precision = 5, scale = 4)
    private BigDecimal platformFeeRate;

    @Lob
    @Column(name = "GatewayFeePolicy")
    private String gatewayFeePolicy;

    @ColumnDefault("'Draft'")
    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    @Column(name = "CreatedAtUtc", nullable = false)
    private Instant createdAtUtc;

    @Column(name = "CreatedBy")
    private Long createdBy;

    @Column(name = "UpdatedAtUtc")
    private Instant updatedAtUtc;

    @Column(name = "UpdatedBy")
    private Long updatedBy;

    @ColumnDefault("0")
    @Column(name = "IsDeleted", nullable = false)
    private Byte isDeleted;

    @Column(name = "DeletedAtUtc")
    private Instant deletedAtUtc;

    @Column(name = "DeletedBy")
    private Long deletedBy;

    @OneToMany(mappedBy = "event")
    private Set<EventRegistration> eventRegistrations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event")
    private Set<Transaction> transactions = new LinkedHashSet<>();

}