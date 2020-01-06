package tiw1.SpringBootTP3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiw1.SpringBootTP3.model.Abonne;
import tiw1.SpringBootTP3.repository.AbonneRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AbonneServiceImpl implements AbonneService {

    private final AbonneRepository AbonneRepository;
    private final String ABONNES_JSON = "abonnes.json";
    private Path path = Paths.get(ABONNES_JSON);
    private ObjectMapper mapper = new ObjectMapper();
    private static List<Abonne> abonnes = new ArrayList<>();

    @Autowired
    public AbonneServiceImpl(AbonneRepository AbonneRepository) {
        this.AbonneRepository = AbonneRepository;
    }

    @Override
    public Optional<Abonne> get(long id) {
        return AbonneRepository.findById(id);
    }

    @Override
    public List<Abonne> getAll() {
        return AbonneRepository.findAll();
    }

    @Override
    public Abonne add() {
        Abonne Abonne = new Abonne();
        return AbonneRepository.saveAndFlush(Abonne);
    }

    @Override
    public void delete(long id) {
        AbonneRepository.deleteById(id);
    }
}
