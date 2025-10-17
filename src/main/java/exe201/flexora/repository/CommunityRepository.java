package exe201.flexora.repository;

import exe201.flexora.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findByIdAndIsDeletedFalse(Long id);
    List<Community> findAllByIsDeletedFalseAndIsPublicTrue();
    List<Community> findAllByIsDeletedFalseAndIsPublicFalse();

}
