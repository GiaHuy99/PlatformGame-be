package exe201.flexora.service;

import exe201.flexora.dto.ClubDTO;

import java.util.List;

public interface ClubService {
    ClubDTO createClub(Long communityId, ClubDTO clubDTO,Long creatorrId);
    ClubDTO getClubById(Long id);
    ClubDTO updateClub(Long id, ClubDTO clubDTO);
    void deleteClub(Long id);
    List<ClubDTO> getAllClubsPublic();
    List<ClubDTO> getAllClubsPrivate();
}
