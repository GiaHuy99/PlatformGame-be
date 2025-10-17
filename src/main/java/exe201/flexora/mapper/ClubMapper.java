package exe201.flexora.mapper;

import exe201.flexora.dto.ClubDTO;
import exe201.flexora.entity.Club;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClubMapper {
    ClubDTO toDto(Club club);

    // Map DTO to new entity (ignore audits and relationships)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "community", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    // Ignore all audit fields
    @Mapping(target = "createdAtUtc", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAtUtc", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "deletedAtUtc", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Club toEntity(ClubDTO dto);


}
