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

    @GetMapping(value = "/emprunt/add/{idAbonne}/{idTrottinette}")
    public List<Emprunt> add(@PathVariable long idAbonne, @PathVariable long idTrottinette) {
        empruntService.save(new Emprunt(idAbonne, idTrottinette));
        return empruntService.getAll();
    }

    @GetMapping(value = "/emprunt/delete/{id}")
    public List<Emprunt> delete(@PathVariable long id) {

        empruntService.delete(id);
        return empruntService.getAll();
    }

    @GetMapping(value = "/emprunt/activate/{id}")
    public List<Emprunt> activate(@PathVariable long id) {
        empruntService.activateEmprunt(id);
        return empruntService.getAll();
    }
}
