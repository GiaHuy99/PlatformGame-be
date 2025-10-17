package exe201.flexora.mapper;

import exe201.flexora.dto.ClubMemberDTO;
import exe201.flexora.entity.ClubMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClubMemberMapper {

    // --- ÁNH XẠ TỪ ENTITY SANG DTO ---
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.avatarUrl", target = "userAvatarUrl")
    @Mapping(source = "club.id", target = "clubId")
    @Mapping(source = "club.name", target = "clubName")
    ClubMemberDTO toDto(ClubMember clubMember);
    // --- ÁNH XẠ TỪ DTO SANG ENTITY ---
    // Phương thức này ít được sử dụng vì việc tạo mới ClubMember
    // thường cần logic phức tạp hơn trong Service.
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "club", ignore = true)
    @Mapping(target = "joinedAt", ignore = true)
    ClubMember toEntity(ClubMemberDTO clubMemberDTO);
}
