package tiw1.SpringBootTP3.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tiw1.SpringBootTP3.model.Emprunt;
import tiw1.SpringBootTP3.service.EmpruntService;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@RestController
public class EmpruntControleur {

    private final EmpruntService<Emprunt> empruntService;

    @Autowired
    public EmpruntControleur(EmpruntService<Emprunt> empruntService) throws Exception {
        this.empruntService = empruntService;
    }

    @GetMapping(value = "/emprunt")
    public List<Emprunt> getAll() { return empruntService.getAll(); }

    @GetMapping(value = "/emprunt/{id}")
    public Optional<Emprunt> get(@PathVariable long id) {
        return empruntService.get(id);
    }

    @GetMapping(value = "/emprunt/add/{id}/{idAbonne}")
    public List<Emprunt> add(@PathVariable long id, @PathVariable long idAbonne) {
        Emprunt emprunt = new Emprunt();
        emprunt.setId(id);
        emprunt.setIdAbonne(idAbonne);
        emprunt.setDate(new Date());
        emprunt.isActif(false);
        emprunt.setIdTrottinette((long) 1);

        empruntService.save(emprunt);
        return empruntService.getAll();
    }

    @GetMapping(value = "/emprunt/delete/{id}")
    public List<Emprunt> delete(@PathVariable long id) {

        empruntService.delete(id);
        return empruntService.getAll();
    }

    @GetMapping(value = "/emprunt/activation/{id}")
    public Optional<Emprunt> activate(@PathVariable long id) {

        return empruntService.get(id);
    }
}
