package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "communities", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Lob
    @Column(name = "Name")
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @Lob
    @Column(name = "School")
    private String school;

    private Boolean isPublic;

    private int membersCount;

    private LocalDateTime createdAtUtc;


    private Long createdBy;


    private LocalDateTime updatedAtUtc;

    private long updatedBy;

    private Boolean isDeleted;

    private LocalDateTime deletedAtUtc;

    private long deletedBy;

    @OneToMany(mappedBy = "community")
    private Set<Club> clubs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "community")
    private Set<Event> events = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "community_games",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> games = new HashSet<>();
}