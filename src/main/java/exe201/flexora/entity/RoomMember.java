package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_members")
@IdClass(RoomMemberId.class)
@Data
public class RoomMember {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "Id")
    private Room room;

    // --- Các thuộc tính bổ sung cho mối quan hệ ---

    @Column(name = "role", nullable = false, length = 50)
    private String role; // Ví dụ: "Host", "Member"

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
}
