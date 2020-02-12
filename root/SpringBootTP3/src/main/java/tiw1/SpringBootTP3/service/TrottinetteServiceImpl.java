package tiw1.SpringBootTP3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiw1.SpringBootTP3.model.Trottinette;
import tiw1.SpringBootTP3.repository.TrottinetteRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class TrottinetteServiceImpl implements TrottinetteService {

    private final TrottinetteRepository trottinetteRepository;

    @Autowired
    public TrottinetteServiceImpl(TrottinetteRepository trottinetteRepository) {
        this.trottinetteRepository = trottinetteRepository;
    }

    @Override
    public Optional<Trottinette> get(long id) {
        return trottinetteRepository.findById(id);
    }

    @Override
    public List<Trottinette> getAll() {
        return trottinetteRepository.findAll();
    }

    @Override
    public boolean isDisponible(long id) { Trottinette t = trottinetteRepository.findById(id).orElse(null); if (t == null) {return Boolean.parseBoolean(null);} else {return t.isDisponible();} }

    @Override
    public void add() {
        trottinetteRepository.save(new Trottinette());
    }

    @Override
    public void delete(long id) {
        trottinetteRepository.deleteById(id);
    }

    @Override
    public void borrow(long id) throws Exception {
        Trottinette trottinette = get(id).orElse(null);
        if (trottinette != null && trottinette.isDisponible()) {
            Objects.requireNonNull(get(id).orElse(null)).setDisponible(false);
        } else {
            throw new Exception("La trottinette " + id + " est déjà prise !");
        }
    }

    @Override
    public void giveBack(long id) throws Exception {
        Trottinette trottinette = get(id).orElse(null);
        if (trottinette != null && !trottinette.isDisponible()) {
            Objects.requireNonNull(get(id).orElse(null)).setDisponible(true);
        } else {
            throw new Exception("Pas de trottinette d'id " + id + " à rendre !");
        }
    }
}
