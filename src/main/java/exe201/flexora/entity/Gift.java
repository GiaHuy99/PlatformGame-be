package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "gifts", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "Name", nullable = false)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @ColumnDefault("0")
    @Column( nullable = false)
    private Integer costPoints;

    private Integer stockQty;

    @Column( nullable = false)
    private Byte isActive;

    @Column( nullable = false)
    private Instant createdAtUtc;

    private Long createdBy;

    private Instant updatedAtUtc;

    private Long updatedBy;

    @Column( nullable = false)
    private Byte isDeleted;

    private Instant deletedAtUtc;

    private Long deletedBy;

}