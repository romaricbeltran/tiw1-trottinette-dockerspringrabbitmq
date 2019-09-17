package tiw1.maintenance.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiw1.maintenance.metier.Maintenance;
import tiw1.maintenance.models.Batterie;
import tiw1.maintenance.models.Trottinette;

import java.util.List;

@RestController
@RequestMapping("/batterie")
public class BatterieController {

    @Autowired
    private Maintenance m;

    @GetMapping
    public List<Batterie> getBatteries() {
        List<Batterie> batteries = m.getBatteries();
        return batteries;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Batterie> getBatterie(@PathVariable long id) {
        final Batterie batterie = m.getBatterie(id);
        if (batterie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(batterie, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Batterie> addBatterie() {
        return new ResponseEntity<Batterie>(m.creerBatterie(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBatterie(@PathVariable long id) {
        m.supprimerBatterie(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

