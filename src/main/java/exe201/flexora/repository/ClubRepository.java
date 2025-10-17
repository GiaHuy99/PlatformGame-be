package exe201.flexora.repository;

import exe201.flexora.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByIdAndIsDeletedFalse(Long id);
    List<Club> findAllByIsPublicTrue();
     List<Club> findAllByIsPublicFalse();

}
