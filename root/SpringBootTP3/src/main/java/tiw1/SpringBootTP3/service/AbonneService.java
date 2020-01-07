package tiw1.SpringBootTP3.service;

import tiw1.SpringBootTP3.model.Abonne;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AbonneService {

    Optional<Abonne> get(long id);

    List<Abonne> getAll();

    void add() throws IOException;

    void delete(long id) throws IOException;

    void persist() throws IOException;

    void read() throws IOException;
}
