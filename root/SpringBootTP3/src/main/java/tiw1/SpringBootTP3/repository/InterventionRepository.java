package tiw1.SpringBootTP3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiw1.SpringBootTP3.model.Intervention;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
}
