package exe201.flexora.repository;

import exe201.flexora.entity.RoomMember;
import exe201.flexora.entity.RoomMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberId> {
    boolean  existsByUser_IdAndRoom_Id( Long userId,Long roomId);
}
