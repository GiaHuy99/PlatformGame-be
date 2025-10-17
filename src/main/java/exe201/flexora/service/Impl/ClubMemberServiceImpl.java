package exe201.flexora.service.Impl;

import exe201.flexora.dto.ClubMemberDTO;
import exe201.flexora.entity.Club;
import exe201.flexora.entity.ClubMember;
import exe201.flexora.entity.User;
import exe201.flexora.mapper.ClubMemberMapper;
import exe201.flexora.repository.ClubMemberRepository;
import exe201.flexora.repository.ClubRepository;
import exe201.flexora.repository.UserRepository;
import exe201.flexora.service.ClubMemberService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ClubMemberServiceImpl implements ClubMemberService {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private ClubMemberMapper clubMemberMapper;
    @Override
    @Transactional // <--- THÊM ANNOTATION NÀY VÀO ĐÂY
    public ClubMemberDTO joinClub(Long userId, Long clubId) {
        // 1. Kiểm tra xem đã là thành viên chưa
        if (clubMemberRepository.existsByUser_IdAndClub_Id(userId, clubId)) {
            throw new RuntimeException("User is already a member of the club.");
        }
        // 2. Lấy các entity cần thiết
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found with id: " + clubId));
        // 3. Tạo đối tượng thành viên mới
        ClubMember newMember = new ClubMember();
        newMember.setUser(user);
        newMember.setClub(club);
        newMember.setRole("Member"); // Vai trò mặc định
        newMember.setJoinedAt(LocalDateTime.now());
        // 4. Lưu thành viên mới
        ClubMember savedMember = clubMemberRepository.save(newMember);
        // 5. Cập nhật lại số lượng thành viên của Club
        club.setMembersCount(club.getMembersCount() + 1);
        clubRepository.save(club);
        // 6. Trả về DTO
        return clubMemberMapper.toDto(savedMember);
    }

    @Override
    @Transactional // <--- THÊM ANNOTATION NÀY VÀO ĐÂY
    public void kickClubMember(Long performerId, Long targetUserId, Long clubId) throws Exception {
        // BƯỚC 1: KIỂM TRA QUYỀN HẠN CỦA NGƯỜI THỰC HIỆN
        ClubMember performer = clubMemberRepository.findByUser_IdAndClub_Id(performerId, clubId)
                .orElseThrow(() -> new Exception("Bạn không phải là thành viên của club này."));

        if (!"ADMIN".equals(performer.getRole())) {
            throw new Exception("Chỉ ADMIN mới có quyền loại thành viên khỏi club.");
        }
        // BƯỚC 2: KIỂM TRA LOGIC (Admin không thể tự kick mình)
        if (performerId.equals(targetUserId)) {
            throw new BadRequestException("Bạn không thể tự xóa chính mình. Hãy dùng chức năng 'Rời khỏi club'.");
        }
        // BƯỚC 3: KIỂM TRA SỰ TỒN TẠI CỦA NGƯỜI BỊ XÓA
        if (!clubMemberRepository.existsByUser_IdAndClub_Id(targetUserId, clubId)) {
            throw new Exception("Thành viên bạn muốn xóa không tồn tại trong club này.");
        }
        // BƯỚC 4: THỰC HIỆN HÀNH ĐỘNG XÓA
        clubMemberRepository.deleteByUser_IdAndClub_Id(targetUserId, clubId);
        // BƯỚC 5: CẬP NHẬT LẠI SỐ LƯỢNG THÀNH VIÊN
        // (Chúng ta lấy club từ performer để cho hiệu quả)
        Club club = performer.getClub();
        club.setMembersCount(club.getMembersCount() - 1);
        clubRepository.save(club) ;
    }
}
