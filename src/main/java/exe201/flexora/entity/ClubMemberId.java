package exe201.flexora.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
@AllArgsConstructor
@NoArgsConstructor
public class ClubMemberId implements Serializable {
    private Long user; // Tên phải khớp với trường 'user' trong ClubMember
    private Long club;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClubMemberId that = (ClubMemberId) o;
        return Objects.equals(user, that.user) && Objects.equals(club, that.club);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, club);
    }
}
