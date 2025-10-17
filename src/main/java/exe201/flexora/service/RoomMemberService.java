package exe201.flexora.service;

import exe201.flexora.dto.RoomMemberDTO;

public interface RoomMemberService {
    RoomMemberDTO joinRoom(Long userId, Long roomId, String providedPassword) throws Exception;
}
