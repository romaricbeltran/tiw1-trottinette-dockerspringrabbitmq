package tiw1.SpringBootTP3.service;

import fr.univ_lyon1.tiw1_is.emprunt.soap.AutorisationResponse;
import fr.univ_lyon1.tiw1_is.emprunt.soap.TransfertResponse;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import tiw1.SpringBootTP3.model.Emprunt;

import java.util.List;
import java.util.Optional;

public interface EmpruntService<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void delete(long id);

    List<Emprunt> create(long idAbonne, long idTrottinette) throws Exception;

    List<Emprunt> askAutorisation(long idEmprunt, long idCompte);

    @RabbitListener(queuesToDeclare = @Queue( name = "autorisation-queue"))
    List<Emprunt> receiveAutorisation(AutorisationResponse autorisationResponse);

    List<Emprunt> send(long idEmprunt, long idCompte, long idAutorisation);

    @RabbitListener(queuesToDeclare = @Queue( name = "emprunt-queue"))
    void activate(TransfertResponse transfertResponse) throws Exception;
}
