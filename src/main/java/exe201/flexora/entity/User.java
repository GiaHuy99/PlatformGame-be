package exe201.flexora.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "exe201")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "access_failed_count")
    private Integer accessFailedCount;

    @Lob
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Lob
    @Column(name = "concurrency_stamp")
    private String concurrencyStamp;

    @Lob
    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "created_at_utc")
    private Instant createdAtUtc;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "deleted_at_utc")
    private Instant deletedAtUtc;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "email", length = 256)
    private String email;

    @Column(name = "email_confirmed")
    private Boolean emailConfirmed = false;

    @Column(name = "full_name", length = 256)
    private String fullName;

    @Column(name = "gender", length = 16)
    private String gender;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "level")
    private Integer level;

    @Column(name = "lockout_enabled")
    private Boolean lockoutEnabled = false;

    @Column(name = "lockout_end")
    private Instant lockoutEnd;

    @Column(name = "normalized_email", length = 256)
    private String normalizedEmail;

    @Column(name = "normalized_user_name", length = 256)
    private String normalizedUserName;

    @Lob
    @Column(name = "password_hash")
    private String passwordHash;

    @Lob
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "phone_number_confirmed")
    private Boolean phoneNumberConfirmed = false;

    @Column(name = "points")
    private Integer points;

    @Lob
    @Column(name = "security_stamp")
    private String securityStamp;

    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled = false;

    @Column(name = "university", length = 256)
    private String university;

    @Column(name = "updated_at_utc")
    private Instant updatedAtUtc;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "user_name", length = 256)
    private String userName;

    @OneToMany(mappedBy = "user")
    private Set<BugReport> bugReports = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<EventRegistration> eventRegistrations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "organizer")
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipient")
    private Set<FriendLink> friendLinks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<FriendLink> friendReceiver = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<PaymentIntent> paymentIntents = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<RefreshToken> refreshTokens = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserClaim> userClaims = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserGame> userGames = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user")
    private Wallet wallet;

}