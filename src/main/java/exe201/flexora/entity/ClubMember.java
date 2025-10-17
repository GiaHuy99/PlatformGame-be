package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "club_member", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@IdClass(ClubMemberId.class)
public class ClubMember {


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", referencedColumnName = "Id")
    private Club club;

    // --- Các thuộc tính bổ sung cho mối quan hệ ---

    @Column(name = "role", nullable = false, length = 50)
    private String role; // Ví dụ: "Admin", "Member"

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
}
