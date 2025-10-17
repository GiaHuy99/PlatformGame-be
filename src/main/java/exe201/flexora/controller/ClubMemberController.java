package exe201.flexora.controller;

import exe201.flexora.dto.ClubMemberDTO;
import exe201.flexora.dto.JoinClubDTO;
import exe201.flexora.dto.LeaveRoomDTO;
import exe201.flexora.service.ClubMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/club-members")
public class ClubMemberController {
    @Autowired
    private ClubMemberService clubMemberService;

    @PostMapping("/{clubId}/join")
    public ResponseEntity<ClubMemberDTO> joinClub(@PathVariable Long clubId,
                                                  @RequestBody JoinClubDTO request) {

        ClubMemberDTO newMembership = clubMemberService.joinClub(request.getUserId(), clubId);
        return ResponseEntity.ok(newMembership); // Trả về status 200 OK cùng với DTO
    }
    @DeleteMapping("/{clubId}/members/{targetUserId}")
    public ResponseEntity<Void> kickClubMember(
            @PathVariable Long clubId,
            @PathVariable Long targetUserId,
            @RequestParam Long adminId) throws Exception { // 'adminId' chính là 'performerId'

        clubMemberService.kickClubMember(adminId, targetUserId, clubId);

        // Trả về 204 No Content - chuẩn cho một lệnh DELETE thành công
        return ResponseEntity.noContent().build();
    }


}
