package tiw1.SpringBootTP3.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tiw1.SpringBootTP3.model.Emprunt;
import tiw1.SpringBootTP3.service.EmpruntService;

import java.util.List;
import java.util.Optional;

@RestController
public class EmpruntControleur {

    private final EmpruntService<Emprunt> empruntService;

    @Autowired
    public EmpruntControleur(EmpruntService<Emprunt> empruntService) throws Exception {
        this.empruntService = empruntService;
    }

    @GetMapping(value = "/emprunt")
    public List<Emprunt> getAll() {
        return empruntService.getAll();
    }

    @GetMapping(value = "/emprunt/{id}")
    public Optional<Emprunt> get(@PathVariable long id) {
        return empruntService.get(id);
    }

    @PostMapping(value = "/emprunt/create/{idAbonne}/{idTrottinette}")
    public List<Emprunt> create(@PathVariable long idAbonne, @PathVariable long idTrottinette) throws Exception {
        empruntService.create(idAbonne, idTrottinette);
        return empruntService.getAll();
    }

    @DeleteMapping(value = "/emprunt/delete/{id}")
    public List<Emprunt> delete(@PathVariable long id) {
        empruntService.delete(id);
        return empruntService.getAll();
    }

    @PutMapping(value = "/emprunt/autorisation/{idEmprunt}/{idCompte}")
    public List<Emprunt> autorisation(@PathVariable long idEmprunt, @PathVariable long idCompte) {
        empruntService.askAutorisation(idEmprunt, idCompte);
        return empruntService.getAll();
    }

    @PutMapping(value = "/emprunt/send/{idEmprunt}/{idCompte}/{idAutorisation}")
    public List<Emprunt> send(@PathVariable long idEmprunt, @PathVariable long idCompte, @PathVariable long idAutorisation) {
        empruntService.send(idEmprunt, idCompte, idAutorisation);
        return empruntService.getAll();
    }
}
