package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "event_registrations", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "EventId", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Lob
    @Column(name = "Status", nullable = false)
    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "PaidTransactionId")
    private Transaction paidTransaction;

    @Column(name = "RegisteredAt", nullable = false)
    private Instant registeredAt;

    @Column(name = "CheckInAt")
    private Instant checkInAt;

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

    @OneToOne(mappedBy = "eventRegistration")
    private PaymentIntent paymentIntent;

}