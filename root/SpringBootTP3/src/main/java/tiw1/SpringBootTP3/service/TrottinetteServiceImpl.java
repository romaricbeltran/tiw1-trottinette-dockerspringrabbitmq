package tiw1.SpringBootTP3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiw1.SpringBootTP3.model.Trottinette;
import tiw1.SpringBootTP3.repository.TrottinetteRepository;

import java.util.List;
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
}
