package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payment_intents", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Column(name = "AmountCents", nullable = false)
    private Long amountCents;

    @Lob
    @Column(name = "Purpose", nullable = false)
    private String purpose;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventRegistrationId")
    private EventRegistration eventRegistration;

    @Lob
    @Column(name = "Status", nullable = false)
    private String status;

    @Lob
    @Column(name = "ClientSecret", nullable = false)
    private String clientSecret;

    @Column(name = "ExpiresAt", nullable = false)
    private Instant expiresAt;

    @Column(name = "CreatedAtUtc", nullable = false)
    private Instant createdAtUtc;

    @Column(name = "CreatedBy")
    private Long createdBy;

    @Column(name = "UpdatedAtUtc")
    private Instant updatedAtUtc;

    @Column(name = "UpdatedBy")
    private Long updatedBy;

    @Column(name = "IsDeleted", nullable = false)
    private Byte isDeleted;

    @Column(name = "DeletedAtUtc")
    private Instant deletedAtUtc;

    @Column(name = "DeletedBy")
    private Long deletedBy;

}