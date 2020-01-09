package tiw1.SpringBootTP3.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tiw1.SpringBootTP3.model.Emprunt;
import tiw1.SpringBootTP3.service.EmpruntService;

import java.util.List;
import java.util.Optional;

@RestController
public class EmpruntControleur {

    private final EmpruntService empruntService;

    @Autowired
    public EmpruntControleur(EmpruntService empruntService) throws Exception {
        this.empruntService = empruntService;
    }

    @GetMapping(value = "/emprunt")
    public List<Emprunt> getAll() { return empruntService.getAll(); }

    @GetMapping(value = "/emprunt/{id}")
    public Optional<Emprunt> get(@PathVariable long id) {
        return empruntService.get(id);
    }

    @GetMapping(value = "/emprunt/add/{id}")
    public List<Emprunt> add(@PathVariable long id) {
        Emprunt emprunt = new Emprunt();
        emprunt.setId(id);
        emprunt.setIdAbonne((long) 1);
        emprunt.setIdTrottinette((long) 1);

        empruntService.save(emprunt);
        return empruntService.getAll();
    }

    @GetMapping(value = "/emprunt/delete/{id}")
    public List<Emprunt> delete(@PathVariable long id) {

        empruntService.delete(id);
        return empruntService.getAll();
    }
}
