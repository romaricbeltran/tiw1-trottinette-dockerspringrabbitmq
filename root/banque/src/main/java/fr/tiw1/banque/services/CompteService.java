package fr.tiw1.banque.services;

import fr.tiw1.banque.dao.AutorisationRepository;
import fr.tiw1.banque.dao.CompteRepository;
import fr.tiw1.banque.modeles.Autorisation;
import fr.tiw1.banque.modeles.Compte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class CompteService {
    private static final Logger LOG = LoggerFactory.getLogger(CompteService.class);
    private CompteRepository compteRepository;
    private AutorisationRepository autorisationRepository;

    @Autowired
    public CompteService(CompteRepository compteRepository, AutorisationRepository autorisationRepository) {
        this.compteRepository = compteRepository;
        this.autorisationRepository = autorisationRepository;
    }

    public Collection<Compte> allComptes() {
        return compteRepository.findAll();
    }

    public Optional<Compte> getCompte(long id) {
        return compteRepository.findById(id);
    }

    @Transactional
    public Optional<Autorisation> addAutorisation(long id, Autorisation autorisation) {
        Optional<Compte> compteOptional = compteRepository.findById(id);
        if (compteOptional.isPresent()) {
            autorisation = autorisationRepository.save(autorisation);
            compteOptional.get().addAutorisation(autorisation);
            return Optional.of(autorisation);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean transfert(long from, long to, long autorisationId, double montant) {
        Optional<Compte> compteFrom = compteRepository.findById(from);
        Optional<Compte> compteTo = compteRepository.findById(to);
        Optional<Autorisation> autorisation = autorisationRepository.findById(autorisationId);
        if (compteFrom.isEmpty()) {
            LOG.warn("Did not found from: {}",from);
        } else if (compteTo.isEmpty()) {
            LOG.warn("Did not found to: {}",to);
        } else if (autorisation.isEmpty()) {
            LOG.warn("Did not found autorisation: {}",autorisationId);
        } else if (! compteFrom.get().getAutorisations().contains(autorisation.get())) {
            LOG.warn("Autorisation {} not for Compte {}",autorisationId,compteFrom);
        } else if (autorisation.get().isUsed()){
            LOG.warn("Autorisation {} already used", autorisationId);
        } else {
            autorisation.get().setUsed(true);
            compteFrom.get().setValeur(compteFrom.get().getValeur() - montant);
            compteTo.get().setValeur(compteTo.get().getValeur() + montant);
            return true;
        }
        return false;
    }

    @Transactional
    public Compte createCompte(Compte compte) {
        LOG.info("Creating compte, initial valeur: {}", compte.getValeur());
        return compteRepository.save(compte);
    }
}
