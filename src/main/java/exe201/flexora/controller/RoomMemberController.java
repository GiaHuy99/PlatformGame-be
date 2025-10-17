package exe201.flexora.controller;

import exe201.flexora.dto.JoinRoomDTO;
import exe201.flexora.dto.LeaveRoomDTO;
import exe201.flexora.dto.RoomMemberDTO;
import exe201.flexora.service.RoomMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/{roomId}/leave")
    public ResponseEntity<Void> leaveRoom(
            @PathVariable Long roomId,
            @RequestBody LeaveRoomDTO request) throws Exception {

        roomMemberService.leaveRoom(request.getUserId(), roomId);

        // Trả về 204 No Content - là chuẩn cho một hành động thành công không trả về nội dung
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{roomId}/members")
    public ResponseEntity<List<RoomMemberDTO>> getRoomMembers(@PathVariable Long roomId) throws Exception {
        List<RoomMemberDTO> members = roomMemberService.getRoomMembers(roomId);
        return ResponseEntity.ok(members);
    }
}
