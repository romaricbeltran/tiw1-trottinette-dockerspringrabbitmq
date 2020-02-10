package tiw1.SpringBootTP3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiw1.SpringBootTP3.model.Emprunt;
import tiw1.SpringBootTP3.repository.EmpruntRepository;

import java.util.List;
import java.util.Optional;

@Component
public class EmpruntServiceImpl implements EmpruntService<Emprunt> {

    private final EmpruntRepository empruntRepository;
    private final TrottinetteService trottinetteService;

    @Autowired
    public EmpruntServiceImpl(EmpruntRepository empruntRepository, TrottinetteService trottinetteService) {
        this.empruntRepository = empruntRepository;
        this.trottinetteService = trottinetteService;
    }

    @Override
    public Optional<Emprunt> get(long id) {
        return empruntRepository.findById(id);
    }

    @Override
    public List<Emprunt> getAll() {
        return empruntRepository.findAll();
    }

    @Override
    public void save(Emprunt emprunt) {
        empruntRepository.save(emprunt);
    }

    @Override
    public void delete(long id) {
        empruntRepository.deleteById(id);
    }

    public List<Emprunt> create(long idAbonne, long idTrottinette) throws Exception {
/*
        trottinetteService.borrow(idTrottinette);
*/
        save(new Emprunt(idAbonne,idTrottinette));
        return getAll();
    }

    @Override
    public Emprunt activateEmprunt(long id) {
        Emprunt emprunt = get(id).orElse(null);
        if (emprunt != null) {
            emprunt.setActif(true);
            empruntRepository.save(emprunt);
        }
        return emprunt;
    }
}