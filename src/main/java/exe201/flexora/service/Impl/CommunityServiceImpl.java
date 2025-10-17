package exe201.flexora.service.Impl;

import exe201.flexora.dto.CommunityDTO;
import exe201.flexora.dto.GameDTO;
import exe201.flexora.entity.Community;
import exe201.flexora.entity.Game;
import exe201.flexora.mapper.CommunityMapper;
import exe201.flexora.repository.CommunityRepository;
import exe201.flexora.repository.GameRepository;
import exe201.flexora.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public CommunityDTO createCommunity(CommunityDTO communityDTO) {
        // 1. Mapper chỉ ánh xạ các trường cơ bản (name, description,...)
        Community community = communityMapper.toEntity(communityDTO);

        // 2. Service xử lý logic quan hệ với Game
        if (communityDTO.getGameDTO() != null && !communityDTO.getGameDTO().isEmpty()) {
            community.setGames(new HashSet<>()); // Khởi tạo set
            for (GameDTO gameDto : communityDTO.getGameDTO()) {
                Game game = gameRepository.findByName(gameDto.getName())
                        // Gợi ý: Dùng exception cụ thể hơn thay vì RuntimeException
                        .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameDto.getId()));
                community.getGames().add(game);
            }
        }

        // 3. Service chịu trách nhiệm cho logic nghiệp vụ và các trường audit
        // Logic này bây giờ không còn bị thừa nữa, nó là phần chính!
        community.setCreatedAtUtc(LocalDateTime.now());
        community.setIsDeleted(false);
        // 4. Lưu vào database
        Community savedCommunity = communityRepository.save(community);

        // 5. Trả về DTO
        return communityMapper.toDto(savedCommunity);
    }

    @Override
    public Optional<CommunityDTO> getCommunityById(Long id) {
        Optional<Community> community = communityRepository.findByIdAndIsDeletedFalse(id);
        return community.map(communityMapper::toDto);
    }

    @Override
    public List<CommunityDTO> getAllCommunities() {
        List<Community> communities = communityRepository.findAllByIsDeletedFalseAndIsPublicTrue();
        return communities.stream()
                .map(communityMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<CommunityDTO> getAllCommunitiesPrivate() {
        List<Community> communities = communityRepository.findAllByIsDeletedFalseAndIsPublicFalse();
        return communities.stream()
                .map(communityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommunityDTO updateCommunity(Long id, CommunityDTO communityDTO) {
        Community existingCommunity = communityRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Community not found with id: " + id));

        // Cập nhật các trường từ DTO
        existingCommunity.setName(communityDTO.getName());
        existingCommunity.setDescription(communityDTO.getDescription());
        existingCommunity.setSchool(communityDTO.getSchool());
        existingCommunity.setIsPublic(communityDTO.getIsPublic());
        existingCommunity.getGames().clear();
        if (communityDTO.getGameDTO() != null && !communityDTO.getGameDTO().isEmpty()) {
            for (GameDTO gameDto : communityDTO.getGameDTO()) {
                Game game = gameRepository.findByName(gameDto.getName())
                        .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameDto.getId()));
                existingCommunity.getGames().add(game);
            }
        }
        // Set audit fields
        existingCommunity.setUpdatedAtUtc(LocalDateTime.now());
        // Lưu Community đã cập nhật
        Community updatedCommunity = communityRepository.save(existingCommunity);

        return communityMapper.toDto(updatedCommunity);
        // Lưu Community đã cập nhật
    }

    @Override
    public void deleteCommunity(Long id) {
        // Tìm Community theo ID
        Community community = communityRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Community not found with id: " + id));

        // Thực hiện xóa mềm
        community.setIsDeleted(true);
        community.setDeletedAtUtc(LocalDateTime.now());
        community.setDeletedBy(1L); // Thay bằng logic lấy user ID thực tế

        communityRepository.save(community);
    }

    @Override
    public void addGameToCommunity(Long communityId, List<String> gameNames) {
        Community community = communityRepository.findByIdAndIsDeletedFalse(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found with id: " + communityId));
        if (community.getId() == null) {
            throw new IllegalStateException("Community is in an invalid state (no ID)");
        }
        // Khởi tạo games nếu null
        if (community.getGames() == null) {
            community.setGames(new HashSet<>());
        }

        // Loop add từng game từ list
        for (String gameName : gameNames) {
            if (gameName != null && !gameName.trim().isEmpty()) {
                Game game = gameRepository.findByName(gameName.trim())
                        .orElseThrow(() -> new RuntimeException("Game not found with name: " + gameName.trim()));
                community.getGames().add(game);  // Set tự handle duplicate
            }
        }
        communityRepository.save(community);
    }
}
