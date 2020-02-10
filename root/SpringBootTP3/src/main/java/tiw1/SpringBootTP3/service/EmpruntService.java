package tiw1.SpringBootTP3.service;

import tiw1.SpringBootTP3.model.Emprunt;

import java.util.List;
import java.util.Optional;

public interface EmpruntService<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void delete(long id);

    Emprunt activateEmprunt(long id);

    List<Emprunt> create(long idAbonne, long idTrottinette) throws Exception;
}
