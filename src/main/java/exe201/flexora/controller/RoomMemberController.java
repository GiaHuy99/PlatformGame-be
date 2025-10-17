package exe201.flexora.controller;

import exe201.flexora.dto.JoinRoomDTO;
import exe201.flexora.dto.RoomMemberDTO;
import exe201.flexora.service.RoomMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room-members")
public class RoomMemberController{
    @Autowired
    private RoomMemberService roomMemberService;

    @PostMapping("/{roomId}/join")
    public ResponseEntity<RoomMemberDTO> joinRoom( @PathVariable Long roomId,
                                                   @RequestBody JoinRoomDTO joinRoomDTO) throws Exception {

        RoomMemberDTO newMembership = roomMemberService.joinRoom(
                joinRoomDTO.getUserId(),
                roomId,
                joinRoomDTO.getPassWord()
        );

        return ResponseEntity.ok(newMembership);
    }
}
