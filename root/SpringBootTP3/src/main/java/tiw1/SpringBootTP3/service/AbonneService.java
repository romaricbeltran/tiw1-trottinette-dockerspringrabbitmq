package tiw1.SpringBootTP3.service;

import tiw1.SpringBootTP3.model.Abonne;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AbonneService<T> {

        Optional<T> get(long id);

        List<T> getAll();

        void save(T t) throws Exception;

        void update(T t) throws Exception;

        void delete(T t) throws Exception;

        void read() throws IOException;

        Abonne findById(Long id);
}
