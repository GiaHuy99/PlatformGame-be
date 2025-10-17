package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "transactions", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "WalletId", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "EventId")
    private Event event;

    @Column(name = "AmountCents", nullable = false)
    private Long amountCents;

    @Lob
    @Column(name = "Currency", nullable = false)
    private String currency;

    @Lob
    @Column(name = "Direction", nullable = false)
    private String direction;

    @Lob
    @Column(name = "Method", nullable = false)
    private String method;

    @Lob
    @Column(name = "Status", nullable = false)
    private String status;

    @Lob
    @Column(name = "Provider")
    private String provider;

    @Lob
    @Column(name = "ProviderRef")
    private String providerRef;

    @Column(name = "Metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

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

    @OneToOne(mappedBy = "paidTransaction")
    private EventRegistration eventRegistration;

}