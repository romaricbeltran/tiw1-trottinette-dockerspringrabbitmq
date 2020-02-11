package tiw1.SpringBootTP3.service;

import fr.univ_lyon1.tiw1_is.emprunt.soap.ObjectFactory;
import fr.univ_lyon1.tiw1_is.emprunt.soap.TransfertRequest;
import fr.univ_lyon1.tiw1_is.emprunt.soap.TransfertResponse;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
    public void autorisation(long idEmprunt, long idCompte) {

/*        Emprunt emprunt = get(idEmprunt).orElse(null);

        TransfertRequest transfertRequest = objectFactory.createTransfertRequest();
        transfertRequest.setAutorisation(idAutorisation);
        transfertRequest.setFrom(idCompte);
        transfertRequest.setTo(1L);
        transfertRequest.setMontant(emprunt.getMontant());
        transfertRequest.setIdEmprunt(idEmprunt);
        transfertRequest.setResponseQueue("emprunt-queue");

        rabbitService.sendOrder(transfertRequest);

        return getAll();*/
    }


    @Override
    public List<Emprunt> send(long idEmprunt, long idCompte, long idAutorisation) {

        Emprunt emprunt = get(idEmprunt).orElse(null);

        TransfertRequest transfertRequest = objectFactory.createTransfertRequest();
        transfertRequest.setAutorisation(idAutorisation);
        transfertRequest.setFrom(idCompte);
        transfertRequest.setTo(1L);
        transfertRequest.setMontant(emprunt.getMontant());
        transfertRequest.setIdEmprunt(idEmprunt);
        transfertRequest.setResponseQueue("emprunt-queue");

        rabbitService.sendOrder(transfertRequest);

        return getAll();
    }

    @Override
    @RabbitListener(queuesToDeclare = @Queue( name = "emprunt-queue"))
    public void activate(TransfertResponse transfertResponse) throws Exception {
        if(transfertResponse.isTransfertOk()) {
            Emprunt emprunt = get(transfertResponse.getIdEmprunt()).orElse(null);
            if (emprunt != null) {
                emprunt.setActif(true);
            }
        } else {
            Emprunt emprunt = get(transfertResponse.getIdEmprunt()).orElse(null);
            if (emprunt != null) {
                trottinetteService.giveBack(emprunt.getIdTrottinette());
                empruntRepository.delete(emprunt);
            }
        }
    }


}