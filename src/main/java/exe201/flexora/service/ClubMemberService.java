package exe201.flexora.service;

import exe201.flexora.dto.ClubMemberDTO;

public interface ClubMemberService {
     ClubMemberDTO joinClub(Long userId, Long clubId);
     void kickClubMember(Long performerId, Long targetUserId, Long clubId) throws Exception;
}
