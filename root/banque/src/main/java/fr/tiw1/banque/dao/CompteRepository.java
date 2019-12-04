package fr.tiw1.banque.dao;

import fr.tiw1.banque.modeles.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, Long> {
}
