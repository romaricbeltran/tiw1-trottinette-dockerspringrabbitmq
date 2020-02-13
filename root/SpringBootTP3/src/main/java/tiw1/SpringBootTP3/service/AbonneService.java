package tiw1.SpringBootTP3.service;

import tiw1.SpringBootTP3.model.Abonne;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AbonneService<T> {

        Optional<T> get(long id);

        List<T> getAll();

        void save(long id) throws IOException;

        void delete(long id) throws IOException;
}
