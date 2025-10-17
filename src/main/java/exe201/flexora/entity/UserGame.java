package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_games", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGame {
    @EmbeddedId
    private UserGameId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @MapsId("gameId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "GameId", nullable = false)
    private Game game;

    @Column(name = "InGameName", length = 64)
    private String inGameName;

    @Column(name = "AddedAt", nullable = false)
    private Instant addedAt;

    @Lob
    @Column(name = "Skill")
    private String skill;

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