package exe201.flexora.mapper;

import exe201.flexora.dto.RoomDTO;
import exe201.flexora.dto.ShowRoomDTO;
import exe201.flexora.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    // toEntity chỉ cần bỏ qua các trường quan hệ và audit.
    // Service sẽ chịu trách nhiệm thiết lập các giá trị này.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "club", ignore = true)

    // Bỏ qua TOÀN BỘ các trường audit
    @Mapping(target = "createdAtUtc", ignore = true)
    @Mapping(target = "updatedAtUtc", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAtUtc", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Room toEntity(RoomDTO roomDto);

    // toDto của bạn đã ổn
    ShowRoomDTO toDto(Room room);

    RoomDTO roomToDto(Room room);

}
