package exe201.flexora.repository;

import exe201.flexora.entity.ClubMember;
import exe201.flexora.entity.ClubMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, ClubMemberId> {

    // MỚI: Kiểm tra xem một người dùng có phải là thành viên của câu lạc bộ không
    boolean existsByUser_IdAndClub_Id(Long userId, Long clubId);

    // MỚI: Tìm một thành viên cụ thể để kiểm tra vai trò
    Optional<ClubMember> findByUser_IdAndClub_Id(Long userId, Long clubId);

    // MỚI: Xóa một thành viên. Spring Data JPA sẽ tự tạo query
    // Cần @Transactional ở Service để hàm này hoạt động
    void deleteByUser_IdAndClub_Id(Long userId, Long clubId);
}
