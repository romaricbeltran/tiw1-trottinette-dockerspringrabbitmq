package fr.tiw1.banque.dao;

import fr.tiw1.banque.modeles.Autorisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorisationRepository extends JpaRepository<Autorisation,Long> {
}
