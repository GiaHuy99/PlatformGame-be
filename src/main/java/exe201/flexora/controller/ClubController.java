package exe201.flexora.controller;

import exe201.flexora.dto.ClubDTO;
import exe201.flexora.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    @Autowired
    private ClubService clubService;
    @PostMapping("/community/{communityId}/user/{creatorId}")
    public ClubDTO createClub(@PathVariable Long communityId,@RequestBody ClubDTO clubDTO,@PathVariable Long creatorId){
        return clubService.createClub(communityId, clubDTO, creatorId);
    }
    @GetMapping("/{id}")
    public ClubDTO getClubById( @PathVariable Long id){
        return clubService.getClubById(id);
    }
    @PatchMapping("/{id}")
    public ClubDTO updateClub(@PathVariable Long id,@RequestBody ClubDTO clubDTO){
        return clubService.updateClub(id, clubDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteClub(@PathVariable Long id){
        clubService.deleteClub(id);
    }
    @GetMapping("/public")
    public java.util.List<ClubDTO> getAllClubsPublic(){
        return clubService.getAllClubsPublic();
    }
    @GetMapping("/private")
    public java.util.List<ClubDTO> getAllClubsPrivate(){
        return clubService.getAllClubsPrivate();
    }
}
