package exe201.flexora.repository;

import exe201.flexora.entity.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugReportRepository  extends JpaRepository<BugReport, Long> {
}
