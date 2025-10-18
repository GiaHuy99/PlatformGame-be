package exe201.flexora.repository;

import exe201.flexora.entity.RoomMember;
import exe201.flexora.entity.RoomMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberId> {
    boolean  existsByUser_IdAndRoom_Id( Long userId,Long roomId);
    void deleteByUser_IdAndRoom_Id(Long userId, Long roomId);
    List<RoomMember> findByRoom_Id(Long roomId);
    Optional<RoomMember> findByUser_IdAndRoom_Id(Long userId, Long roomId);
}
