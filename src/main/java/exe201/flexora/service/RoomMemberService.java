package exe201.flexora.service;

import exe201.flexora.dto.RoomMemberDTO;

import java.util.List;

public interface RoomMemberService {
    RoomMemberDTO joinRoom(Long userId, Long roomId, String providedPassword) throws Exception;
    void leaveRoom(Long userId, Long roomId) throws Exception;
    public List<RoomMemberDTO> getRoomMembers(Long roomId) throws Exception;
}
