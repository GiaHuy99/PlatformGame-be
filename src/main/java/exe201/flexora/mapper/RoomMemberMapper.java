package exe201.flexora.mapper;

import exe201.flexora.dto.RoomMemberDTO;
import exe201.flexora.entity.RoomMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMemberMapper {
    /**
     * Chuyển đổi từ Entity RoomMember sang RoomMemberDTO.
     * Dùng để trả về dữ liệu cho client qua API.
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.avatarUrl", target = "userAvatarUrl")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.name", target = "roomName")
    RoomMemberDTO toDto(RoomMember roomMember);

    /**
     * Chuyển đổi từ RoomMemberDTO sang Entity RoomMember.
     * Ít được sử dụng trực tiếp vì Service cần lấy User và Room từ DB.
     */
    @Mapping(target = "user", ignore = true)     // Sẽ được set thủ công trong Service
    @Mapping(target = "room", ignore = true)     // Sẽ được set thủ công trong Service
    @Mapping(target = "joinedAt", ignore = true) // Service sẽ set thời gian hiện tại
    RoomMember toEntity(RoomMemberDTO roomMemberDTO);
}
