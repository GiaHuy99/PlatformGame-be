package exe201.flexora.service.Impl;

import exe201.flexora.dto.ChatMessageDTO;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
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

        ChatMessageDTO joinMessage = new ChatMessageDTO();
        joinMessage.setType(ChatMessageDTO.MessageType.JOIN);
        joinMessage.setSender("Hệ thống");
        joinMessage.setRoomId(roomId);
        joinMessage.setContent(user.getUserName() + " đã tham gia phòng!");
        messagingTemplate.convertAndSend("/topic/room/" + roomId, joinMessage);
        // Cập nhật số lượng thành viên trong phòng
        room.setMembersCount(room.getMembersCount() + 1);
        roomRepository.save(room);

        return roomMemberMapper.toDto(savedMember);
    }

    @Override
    @Transactional
    public void leaveRoom(Long userId, Long roomId) throws Exception {
        // TỐI ƯU: Lấy thẳng đối tượng RoomMember.
        // Thao tác này vừa kiểm tra sự tồn tại, vừa cho chúng ta truy cập vào User và Room.
        RoomMember member = roomMemberRepository.findByUser_IdAndRoom_Id(userId, roomId)
                .orElseThrow(() -> new Exception(
                        "Bạn không phải là thành viên của phòng này."));

        // LỖI ĐÃ SỬA: Lấy thông tin User và Room trực tiếp từ đối tượng member đã lấy ra.
        User user = member.getUser();
        Room room = member.getRoom();

        // HÀNH ĐỘNG #1: Xóa bản ghi thành viên
        roomMemberRepository.delete(member); // Xóa bằng đối tượng hiệu quả hơn

        // HÀNH ĐỘNG #2: Cập nhật lại số lượng thành viên
        int currentCount = room.getMembersCount();
        room.setMembersCount(Math.max(0, currentCount - 1));
        // KHÔNG CẦN roomRepository.save(room); nữa vì @Transactional sẽ tự lo.

        // Gửi thông báo WebSocket sau khi logic đã xử lý xong
        ChatMessageDTO leaveMessage = new ChatMessageDTO();
        leaveMessage.setType(ChatMessageDTO.MessageType.LEAVE);
        leaveMessage.setSender("Hệ thống"); // Hoặc user.getUserName() nếu bạn muốn
        leaveMessage.setRoomId(roomId);
        leaveMessage.setContent(user.getUserName() + " đã rời khỏi phòng!");
        messagingTemplate.convertAndSend("/topic/room/" + roomId, leaveMessage);

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
