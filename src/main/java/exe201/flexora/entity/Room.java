package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;


@Entity
@Table(name = "rooms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "Name", nullable = false)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @Lob
    private String joinPolicy;

    @Column( length = 256)
    private String joinPasswordHash;

    @Column(name = "Capacity")
    private int capacity;

    private int membersCount;

    private LocalDateTime createdAtUtc;

    private Long createdBy;

    private LocalDateTime updatedAtUtc;

    private Long updatedBy;

    private boolean isDeleted;

    private LocalDateTime deletedAtUtc;

    private Long deletedBy;

}