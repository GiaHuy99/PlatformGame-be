package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Lob
    @Column(name = "TokenHash", nullable = false)
    private String tokenHash;

    @Column(name = "ExpiresAtUtc", nullable = false)
    private Instant expiresAtUtc;

    @Lob
    @Column(name = "CreatedByIp")
    private String createdByIp;

    @Lob
    @Column(name = "UserAgent")
    private String userAgent;

    @Column(name = "RevokedAtUtc")
    private Instant revokedAtUtc;

    @Lob
    @Column(name = "RevokedByIp")
    private String revokedByIp;

    @Lob
    @Column(name = "ReasonRevoked")
    private String reasonRevoked;

    @Column(name = "ReplacedByTokenId")
    private Long replacedByTokenId;

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