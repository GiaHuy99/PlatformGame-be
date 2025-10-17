package exe201.flexora.controller;

import exe201.flexora.dto.CommunityDTO;
import exe201.flexora.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/community")
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    @PostMapping
    public ResponseEntity<CommunityDTO> createCommunity(@RequestBody CommunityDTO dto) {
        return ResponseEntity.ok(communityService.createCommunity(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CommunityDTO>> getCommunityById(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.getCommunityById(id));
    }

    @GetMapping
    public ResponseEntity<List<CommunityDTO>> getAllCommunities() {
        return ResponseEntity.ok(communityService.getAllCommunities());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommunityDTO> updateCommunity(@PathVariable Long id, @RequestBody CommunityDTO dto) {
        return ResponseEntity.ok(communityService.updateCommunity(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{communityId}")
    public ResponseEntity<String> addGameToCommunity(@PathVariable("communityId") Long communityId,@RequestBody List<String> gameNames) {
        try {
            System.out.println(" to community " + communityId);
            communityService.addGameToCommunity(communityId,  gameNames);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/private")
    public ResponseEntity<List<CommunityDTO>> getAllCommunitiesPrivate() {
        return ResponseEntity.ok(communityService.getAllCommunitiesPrivate());
    }
}
