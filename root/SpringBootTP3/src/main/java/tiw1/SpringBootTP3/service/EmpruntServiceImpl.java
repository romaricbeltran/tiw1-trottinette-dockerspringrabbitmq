package tiw1.SpringBootTP3.service;

import fr.univ_lyon1.tiw1_is.emprunt.soap.ObjectFactory;
import fr.univ_lyon1.tiw1_is.emprunt.soap.TransfertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiw1.SpringBootTP3.model.Emprunt;
import tiw1.SpringBootTP3.rabbitmq.RabbitService;
import tiw1.SpringBootTP3.repository.EmpruntRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class EmpruntServiceImpl implements EmpruntService<Emprunt> {

    private final EmpruntRepository empruntRepository;
    private final TrottinetteService trottinetteService;

    @Autowired
    private RabbitService rabbitService;
    private ObjectFactory objectFactory = new ObjectFactory();

    @Autowired
    public EmpruntServiceImpl(EmpruntRepository empruntRepository, TrottinetteService trottinetteService) {
        this.empruntRepository = empruntRepository;
        this.trottinetteService = trottinetteService;
    }

    @Override
    public Optional<Emprunt> get(long id) {
        return empruntRepository.findById(id);
    }

    @Override
    public List<Emprunt> getAll() {
        return empruntRepository.findAll();
    }

    @Override
    public void save(Emprunt emprunt) {
        empruntRepository.save(emprunt);
    }

    @Override
    public void delete(long id) {
        empruntRepository.deleteById(id);
    }

    public List<Emprunt> create(long idAbonne, long idTrottinette) throws Exception {
        trottinetteService.borrow(idTrottinette);
        save(new Emprunt(idAbonne,idTrottinette));
        return getAll();
    }

    @Override
    public List<Emprunt> send(long idEmprunt, long idCompte, long idAutorisation) {

        Emprunt emprunt = empruntRepository.getOne(idEmprunt);

        TransfertRequest transfertRequest = objectFactory.createTransfertRequest();
        transfertRequest.setAutorisation(idAutorisation);
        transfertRequest.setFrom(idCompte);
        transfertRequest.setTo(1);
        transfertRequest.setMontant(emprunt.getMontant());

        rabbitService.sendOrder(transfertRequest);

        Objects.requireNonNull(get(idEmprunt).orElse(null)).setActif(true);
        return getAll();
    }
}