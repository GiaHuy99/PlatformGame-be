package exe201.flexora.service;

import exe201.flexora.dto.CommunityDTO;

import java.util.List;
import java.util.Optional;

public interface CommunityService {
    CommunityDTO createCommunity(CommunityDTO communityDTO);
    Optional<CommunityDTO> getCommunityById(Long id);
    List<CommunityDTO> getAllCommunities();
    CommunityDTO updateCommunity(Long id, CommunityDTO communityDTO);
    void deleteCommunity(Long id);
    void addGameToCommunity(Long communityId, List<String> gameNames);
    List<CommunityDTO> getAllCommunitiesPrivate();
}
