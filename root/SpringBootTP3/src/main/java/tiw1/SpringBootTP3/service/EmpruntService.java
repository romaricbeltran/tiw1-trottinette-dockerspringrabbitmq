package tiw1.SpringBootTP3.service;

import fr.univ_lyon1.tiw1_is.emprunt.soap.TransfertResponse;
import tiw1.SpringBootTP3.model.Emprunt;

import java.util.List;
import java.util.Optional;

public interface EmpruntService<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void delete(long id);

    List<Emprunt> create(long idAbonne, long idTrottinette) throws Exception;

    void send(long idEmprunt, long idCompte, long idAutorisation);

    void activate(TransfertResponse transfertResponse) throws Exception;
}
