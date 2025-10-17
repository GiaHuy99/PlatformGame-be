package exe201.flexora.service.Impl;

import exe201.flexora.dto.RoomMemberDTO;
import exe201.flexora.entity.Room;
import exe201.flexora.entity.RoomMember;
import exe201.flexora.entity.User;
import exe201.flexora.mapper.RoomMemberMapper;
import exe201.flexora.repository.ClubMemberRepository;
import exe201.flexora.repository.RoomMemberRepository;
import exe201.flexora.repository.RoomRepository;
import exe201.flexora.repository.UserRepository;
import exe201.flexora.service.RoomMemberService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomMemberServiceImpl implements RoomMemberService  {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomMemberRepository roomMemberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private RoomMemberMapper roomMemberMapper;

    @Override
    @Transactional
    public RoomMemberDTO joinRoom(Long userId, Long roomId,String providedPassword)
            throws Exception{
        // Lấy các entity cần thiết từ database
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new Exception("Không tìm thấy phòng với ID: " + roomId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Không tìm thấy người dùng với ID: " + userId));

        // KIỂM TRA #1: Phải là thành viên của Club cha
        Long clubId = room.getClub().getId();
        if (!clubMemberRepository.existsByUser_IdAndClub_Id(userId, clubId)) {
            throw new Exception("Bạn phải là thành viên của club để tham gia phòng này.");
        }

        // KIỂM TRA #2: Người dùng đã ở trong phòng này chưa?
        if (roomMemberRepository.existsByUser_IdAndRoom_Id(userId, roomId)) {
            throw new Exception("Bạn đã ở trong phòng này rồi.");
        }

        // KIỂM TRA #3: Sức chứa của phòng
        if (room.getMembersCount() >= room.getCapacity()) {
            throw new BadRequestException("Phòng đã đầy, không thể tham gia.");
        }

        // KIỂM TRA #4: Mật khẩu (Logic đã được đơn giản hóa)
        String storedPasswordHash = room.getJoinPasswordHash();
        if (storedPasswordHash != null && !storedPasswordHash.isBlank()) {
            // Nếu phòng có mật khẩu, tiến hành kiểm tra
            if (providedPassword == null || !providedPassword.equals(storedPasswordHash)) {
                throw new Exception("Mật khẩu phòng không chính xác.");
            }
        }

        // Nếu vượt qua tất cả kiểm tra, tiến hành tạo thành viên
        RoomMember newMember = new RoomMember();
        newMember.setUser(user);
        newMember.setRoom(room);
        newMember.setRole("Member");
        newMember.setJoinedAt(LocalDateTime.now());
        RoomMember savedMember = roomMemberRepository.save(newMember);


        // Cập nhật số lượng thành viên trong phòng
        room.setMembersCount(room.getMembersCount() + 1);
        roomRepository.save(room);
        return roomMemberMapper.toDto(savedMember);
    }

    @Override
    @Transactional
    public void leaveRoom(Long userId, Long roomId) throws Exception {
        // KIỂM TRA #1: Người dùng có thực sự ở trong phòng không?
        if (!roomMemberRepository.existsByUser_IdAndRoom_Id(userId, roomId)) {
            throw new Exception("Bạn không phải là thành viên của phòng này để có thể rời đi.");
        }

        // Lấy thông tin Room để cập nhật lại số lượng thành viên
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new Exception("Không tìm thấy phòng với ID: " + roomId));

        // HÀNH ĐỘNG #1: Xóa bản ghi thành viên
        roomMemberRepository.deleteByUser_IdAndRoom_Id(userId, roomId);

        // HÀNH ĐỘNG #2: Cập nhật lại số lượng thành viên
        // Đảm bảo count không bao giờ < 0
        int currentCount = room.getMembersCount();
        room.setMembersCount(Math.max(0, currentCount - 1));
        roomRepository.save(room);
    }

    @Override
    public List<RoomMemberDTO> getRoomMembers(Long roomId) throws Exception{
        if (!roomRepository.existsById(roomId)) {
            throw new Exception("Không tìm thấy phòng với ID: " + roomId);
        }

        // BƯỚC 1: Lấy danh sách các entity RoomMember từ database
        List<RoomMember> members = roomMemberRepository.findByRoom_Id(roomId);

        // BƯỚC 2: Dùng Mapper để chuyển đổi danh sách entity sang danh sách DTO
        return members.stream()
                .map(roomMemberMapper::toDto)
                .collect(Collectors.toList());
    }
}
