package tiw1.SpringBootTP3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiw1.SpringBootTP3.model.Emprunt;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
}
