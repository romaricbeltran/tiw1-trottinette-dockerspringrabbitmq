package tiw1.SpringBootTP3.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tiw1.SpringBootTP3.model.Trottinette;
import tiw1.SpringBootTP3.service.TrottinetteLoaderService;
import tiw1.SpringBootTP3.service.TrottinetteService;

import java.util.List;
import java.util.Optional;

@RestController
public class TrottinetteControleur {

    private final TrottinetteService trottinetteService;

    @Autowired
    public TrottinetteControleur(TrottinetteLoaderService trottinetteLoaderService, TrottinetteService trottinetteService) throws Exception {
        trottinetteLoaderService.load();
        this.trottinetteService = trottinetteService;
    }

    @GetMapping(value = "/trottinette")
    public List<Trottinette> getAll() {
        return trottinetteService.getAll();
    }

    @GetMapping(value = "/trottinette/{id}")
    public Optional<Trottinette> get(@PathVariable long id) {
        return trottinetteService.get(id);
    }

    @GetMapping(value = "/trottinette/add")
    public Trottinette add() {
        return trottinetteService.add();
    }

    @GetMapping(value = "/trottinette/delete/{id}")
    public List<Trottinette> delete(@PathVariable long id) {
        trottinetteService.delete(id);
        return trottinetteService.getAll();
    }
}
