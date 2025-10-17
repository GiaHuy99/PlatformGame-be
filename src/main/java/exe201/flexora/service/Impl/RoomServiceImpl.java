package exe201.flexora.service.Impl;

import exe201.flexora.dto.RoomDTO;
import exe201.flexora.dto.ShowRoomDTO;
import exe201.flexora.entity.Club;
import exe201.flexora.entity.Room;
import exe201.flexora.mapper.RoomMapper;
import exe201.flexora.repository.ClubRepository;
import exe201.flexora.repository.RoomRepository;
import exe201.flexora.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomMapper roomMapper;
    @Override
    @Transactional // Rất quan trọng! Đảm bảo tất cả các thao tác đều thành công hoặc thất bại cùng nhau
    public ShowRoomDTO createRoom(RoomDTO roomDTO, Long clubId) {
        // 1. Tìm Club một cách an toàn, nếu không có thì báo lỗi ngay
        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found with id: " + clubId));

        // 2. Để Mapper làm đúng công việc của nó: ánh xạ tất cả các trường từ DTO
        Room newRoom = roomMapper.toEntity(roomDTO);

        // 3. Service chịu trách nhiệm cho logic nghiệp vụ và thiết lập quan hệ
        newRoom.setClub(existingClub); // Thiết lập quan hệ ManyToOne
        newRoom.setMembersCount(0); // Logic: phòng mới chưa có thành viên

        // Logic cho các trường audit (đã được chuyển từ Mapper về đây)
        newRoom.setDeleted(false);
        newRoom.setCreatedAtUtc(LocalDateTime.now());

        // 4. Lưu entity mới vào DB
        Room savedRoom = roomRepository.save(newRoom);

        // 5. Cập nhật quan hệ hai chiều (tùy chọn nhưng nên có để nhất quán)
        // Do có @Transactional, Hibernate sẽ tự động cập nhật `existingClub` khi transaction kết thúc
        if (existingClub.getRooms() == null) {
            existingClub.setRooms(new HashSet<>());
        }
        existingClub.getRooms().add(savedRoom);
        clubRepository.save(existingClub);
        // 6. Trả về DTO
        return roomMapper.toDto(savedRoom);
    }

    @Override
    public ShowRoomDTO getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        return roomMapper.toDto(room);
    }

    @Override
    public RoomDTO updateRoom(Long roomId, RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findById(roomId).orElse(null);

            existingRoom.setName(roomDTO.getName());
            existingRoom.setJoinPasswordHash(roomDTO.getJoinPasswordHash());
            existingRoom.setDescription(roomDTO.getDescription());
            existingRoom.setJoinPolicy(roomDTO.getJoinPolicy());
            existingRoom.setCapacity(roomDTO.getCapacity());
            Room updatedRoom = roomRepository.save(existingRoom);
            return roomMapper.roomToDto(updatedRoom);
    }

    @Override
    public void deleteRoom(Long roomId) {
        Room existingRoom = roomRepository.findById(roomId).orElse(null);
            existingRoom.setDeleted(true);
            existingRoom.setDeletedAtUtc(java.time.LocalDateTime.now());
            roomRepository.save(existingRoom);
    }

    @Override
    public List<ShowRoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAllByIsDeletedFalse();
        return rooms.stream().map(roomMapper::toDto).toList();
    }
}
