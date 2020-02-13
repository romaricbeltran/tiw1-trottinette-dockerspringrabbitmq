package tiw1.SpringBootTP3.service;

import org.springframework.stereotype.Component;
import tiw1.SpringBootTP3.model.Abonne;
import tiw1.SpringBootTP3.repository.AbonneRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class AbonneServiceImpl implements AbonneService<Abonne> {

    private AbonneRepository abonneRepository;

    public AbonneServiceImpl(AbonneRepository abonneRepository) {
        this.abonneRepository = abonneRepository;
    }

    @Override
    public Optional get(long id) {
        return abonneRepository.get(id);
    }

    @Override
    public List getAll() {
        return abonneRepository.getAll();
    }

    @Override
    public void save(long id) throws IOException {
        Optional<Abonne> abonneTmp = abonneRepository.get(id);
        if (abonneTmp.isEmpty()) {
            Abonne abonne = new Abonne();
            abonne.setId(id);
            abonneRepository.save(abonne);
        }
    }


    @Override
    public void delete(long id) throws IOException {
        abonneRepository.delete((Abonne) get(id).orElse(null));
    }
}