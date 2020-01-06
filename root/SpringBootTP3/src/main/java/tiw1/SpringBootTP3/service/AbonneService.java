package tiw1.SpringBootTP3.service;

import tiw1.SpringBootTP3.model.Abonne;

import java.util.List;
import java.util.Optional;

public interface AbonneService {

    Optional<Abonne> get(long id);

    List<Abonne> getAll();

    Abonne add();

    void delete(long id);
}
