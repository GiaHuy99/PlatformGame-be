package exe201.flexora.mapper;

import exe201.flexora.dto.CommunityDTO;
import exe201.flexora.entity.Community;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface CommunityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clubs", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "games", ignore = true)

// Bỏ qua TOÀN BỘ các trường audit. Service sẽ xử lý chúng.
    @Mapping(target = "isDeleted", ignore = true) // THAY ĐỔI Ở ĐÂY
    @Mapping(target = "createdAtUtc", ignore = true) // THAY ĐỔI Ở ĐÂY
    @Mapping(target = "updatedAtUtc", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAtUtc", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Community toEntity(CommunityDTO dto);

    // Phương thức toDto của bạn đã rất tốt, giữ nguyên nó.
    @Mapping(target = "clubCount", expression = "java(entity.getClubs() != null ? entity.getClubs().size() : 0)")
    @Mapping(target = "eventCount", expression = "java(entity.getEvents() != null ? entity.getEvents().size() : 0)")
    @Mapping(target = "gameDTO", source = "games")
    CommunityDTO toDto(Community entity);
}
