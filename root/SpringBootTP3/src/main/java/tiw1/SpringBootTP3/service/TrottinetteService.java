package tiw1.SpringBootTP3.service;

import tiw1.SpringBootTP3.model.Trottinette;

import java.util.List;
import java.util.Optional;

public interface TrottinetteService {

    Optional<Trottinette> get(long id);

    List<Trottinette> getAll();

    boolean isDisponible(long id);

    void add();

    void delete(long id);

    void borrow(long id) throws Exception;

    void giveBack(long id) throws Exception;
}
