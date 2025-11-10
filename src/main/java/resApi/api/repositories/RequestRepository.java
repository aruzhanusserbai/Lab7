package resApi.api.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import resApi.api.entities.ApplicationRequest;

import java.util.List;

@Repository
@Transactional
public interface RequestRepository extends JpaRepository<ApplicationRequest, Long> {
    void deleteOperatorById(Long id);
    List<ApplicationRequest> findByHandledFalse();
    List<ApplicationRequest> findByHandledTrue();
}
