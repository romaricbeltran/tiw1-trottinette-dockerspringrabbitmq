package tiw1.SpringBootTP3.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tiw1.SpringBootTP3.model.Abonne;
import tiw1.SpringBootTP3.service.AbonneService;

import java.util.List;
import java.util.Optional;

@RestController
public class AbonneControleur {

    private final AbonneService AbonneService;

    @Autowired
    public AbonneControleur(AbonneService AbonneService) {
        this.AbonneService = AbonneService;
    }

    @GetMapping(value = "/abonne")
    public List<Abonne> getAll() {
        return AbonneService.getAll();
    }

    @GetMapping(value = "/abonne/{id}")
    public Optional<Abonne> get(@PathVariable long id) {
        return AbonneService.get(id);
    }

    @GetMapping(value = "/abonne/add")
    public Abonne add() {
        return AbonneService.add();
    }

    @GetMapping(value = "/abonne/delete/{id}")
    public List<Abonne> delete(@PathVariable long id) {
        AbonneService.delete(id);
        return AbonneService.getAll();
    }
}
