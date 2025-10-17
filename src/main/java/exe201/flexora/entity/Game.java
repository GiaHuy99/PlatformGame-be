package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "games", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "Name", nullable = false)
    private String name;

    @Column( nullable = false)
    private LocalDateTime createdAtUtc;

    private Long createdBy;

    private LocalDateTime updatedAtUtc;

    private Long updatedBy;

    private boolean isDeleted;

    private LocalDateTime deletedAtUtc;

    private Long deletedBy;

    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
    private Set<Community> communities = new HashSet<>();


}