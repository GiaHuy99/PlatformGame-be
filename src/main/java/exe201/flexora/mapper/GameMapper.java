package exe201.flexora.mapper;

import exe201.flexora.dto.GameDTO;
import exe201.flexora.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface GameMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAtUtc", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAtUtc", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "deletedAtUtc", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)

    Game toEntity(GameDTO dto);

    GameDTO toDto(Game entity);
}

