package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bug_reports", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class BugReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn
    private User user;

    @Lob
    @Column(name = "Category", nullable = false)
    private String category;

    @Lob
    @Column(name = "Description", nullable = false)
    private String description;

    @Lob
    private String imageUrl;

    @Lob
    @Column(name = "Status", nullable = false)
    private String status;

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