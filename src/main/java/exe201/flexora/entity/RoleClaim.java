package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "role_claims", schema = "exe201")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "RoleId", nullable = false)
    private Role role;

    @Lob
    @Column(name = "ClaimType")
    private String claimType;

    @Lob
    @Column(name = "ClaimValue")
    private String claimValue;

}