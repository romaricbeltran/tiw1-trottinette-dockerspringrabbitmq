package tiw1.SpringBootTP3.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tiw1.SpringBootTP3.model.Abonne;
import tiw1.SpringBootTP3.service.AbonneService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class AbonneControleur {

    private final AbonneService abonneService;

    @Autowired
    public AbonneControleur(AbonneService abonneService) throws Exception {
        abonneService.read();
        this.abonneService = abonneService;
    }

    @GetMapping(value = "/abonne")
    public List<Abonne> getAll() {
        return abonneService.getAll();
    }

    @GetMapping(value = "/abonne/{id}")
    public Optional<Abonne> get(@PathVariable long id) {
        return abonneService.get(id);
    }

    @GetMapping(value = "/abonne/add/{id}")
    public List<Abonne> add(@PathVariable long id) throws Exception {
        Abonne abonne = new Abonne();
        abonne.setId(id);
        abonne.setName("TEST");

        abonneService.save(abonne);
        return abonneService.getAll();
    }

    @GetMapping(value = "/abonne/delete/{id}")
    public List<Abonne> delete(@PathVariable long id) throws Exception {
        Abonne abonne = abonneService.findById(id);

        abonneService.delete(abonne);
        return abonneService.getAll();
    }
}
