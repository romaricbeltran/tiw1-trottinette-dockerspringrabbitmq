package tiw1.SpringBootTP3.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tiw1.SpringBootTP3.model.Abonne;
import tiw1.SpringBootTP3.service.AbonneService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AbonneControleur {

    private final AbonneService<Abonne> abonneService;

    @Autowired
    public AbonneControleur(AbonneService<Abonne> abonneService) throws Exception {
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

    @PostMapping(value = "/abonne/add/{id}")
    public List<Abonne> add(@PathVariable long id) throws IOException {
        abonneService.save(id);
        return abonneService.getAll();
    }

    @DeleteMapping(value = "/abonne/delete/{id}")
    public List<Abonne> delete(@PathVariable long id) throws Exception {
        abonneService.delete(id);
        return abonneService.getAll();
    }
}
