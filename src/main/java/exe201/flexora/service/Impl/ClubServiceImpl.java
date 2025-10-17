package exe201.flexora.service.Impl;

import exe201.flexora.dto.ClubDTO;
import exe201.flexora.entity.Club;
import exe201.flexora.entity.ClubMember;
import exe201.flexora.entity.Community;
import exe201.flexora.entity.User;
import exe201.flexora.mapper.ClubMapper;
import exe201.flexora.repository.ClubMemberRepository;
import exe201.flexora.repository.ClubRepository;
import exe201.flexora.repository.CommunityRepository;
import exe201.flexora.repository.UserRepository;
import exe201.flexora.service.ClubService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubMapper clubMapper;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional // Rất quan trọng! Đảm bảo tất cả các thao tác đều thành công hoặc thất bại cùng nhau
    public ClubDTO createClub(Long communityId, ClubDTO clubDTO,Long creatorrId) {
        Community existingCommunity = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));  // Hoặc dùng EntityNotFoundException
        User creator = userRepository.findById(creatorrId)
                .orElseThrow(() -> new RuntimeException("User not found"));  // Hoặc dùng EntityNotFoundException
        // Tạo Club mới từ DTO
        Club newClub = clubMapper.toEntity(clubDTO);
        newClub.setCommunity(existingCommunity);
        newClub.setMembersCount(0);
        newClub.setPublic(clubDTO.getIsPublic());
// ==> Logic được chuyển từ Mapper về đây. Rất rõ ràng!
        newClub.setCreatedAtUtc(LocalDateTime.now());
        newClub.setDeleted(false); // Hoặc newClub.setIsDeleted(false) tùy tên trường
        Club savedClub = clubRepository.save(newClub);
        // Tạo ClubMember cho người tạo với vai trò ADMIN
        ClubMember creatorMembership = new ClubMember();
        creatorMembership.setUser(creator);
        creatorMembership.setClub(savedClub);
        creatorMembership.setRole("ADMIN"); // <-- GÁN VAI TRÒ ADMIN Ở ĐÂY
        creatorMembership.setJoinedAt(LocalDateTime.now());
        clubMemberRepository.save(creatorMembership);
        // Cập nhật danh sách clubs trong Community
        if (existingCommunity.getClubs() == null) {
            existingCommunity.setClubs(new HashSet<>());
        }
        communityRepository.save(existingCommunity); // Cập nhật community trước
        existingCommunity.getClubs().add(savedClub);
        return clubMapper.toDto(savedClub);
    }

    @Override
    public ClubDTO getClubById(Long id) {
       Optional <Club> club = clubRepository.findByIdAndIsDeletedFalse(id);
        return club.map(clubMapper::toDto).orElse(null);
    }

    @Override
    public ClubDTO updateClub(Long id, ClubDTO clubDTO) {
        Club existingClub = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + id));
        existingClub.setName(clubDTO.getName());
        existingClub.setDescription(clubDTO.getDescription());
        existingClub.setPublic(clubDTO.getIsPublic());
        return clubMapper.toDto(clubRepository.save(existingClub));
    }

    @Override
    public void deleteClub(Long id) {
// Tìm entity
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + id));

        // Soft delete: Đánh dấu thay vì xóa vĩnh viễn
        club.setDeleted(true);
        club.setDeletedAtUtc(LocalDateTime.now());
        // Save thay đổi
        clubRepository.save(club);
    }

    @Override
    public List<ClubDTO> getAllClubsPublic() {
        List<Club> clubs = clubRepository.findAllByIsPublicTrue();

        return clubs.stream()
                .map(clubMapper::toDto)
                .toList();
    }

    @Override
    public List<ClubDTO> getAllClubsPrivate() {
        List<Club> clubs = clubRepository.findAllByIsPublicFalse();
        return clubs.stream()
                .map(clubMapper::toDto)
                .toList();
    }
}
