package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "clubs", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @Lob
    @Column(name = "Name", nullable = false)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @Column(name = "is_public", nullable = false)  // FIX: Lowercase name để map dễ
    private boolean isPublic;

    @Column( nullable = false)
    private int membersCount;

    @Column( nullable = false)
    private LocalDateTime createdAtUtc;

    private Long createdBy;

    private LocalDateTime updatedAtUtc;

    private Long updatedBy;

    private boolean isDeleted;

    private LocalDateTime deletedAtUtc;

    private Long deletedBy;

    @OneToMany(mappedBy = "club")
    private Set<Room> rooms = new LinkedHashSet<>();


}