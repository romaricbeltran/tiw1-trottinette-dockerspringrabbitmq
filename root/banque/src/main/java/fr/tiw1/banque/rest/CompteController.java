package fr.tiw1.banque.rest;

import fr.tiw1.banque.modeles.Autorisation;
import fr.tiw1.banque.modeles.Compte;
import fr.tiw1.banque.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/compte")
public class CompteController {
    @Autowired
    private CompteService compteService;

    @GetMapping
    public ResponseEntity<Collection<Compte>> allComptes() {
        return new ResponseEntity<Collection<Compte>>(compteService.allComptes(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Compte> createCompte(Compte compte) {
        return new ResponseEntity<>(compteService.createCompte(compte), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompte(@PathVariable long id) {
        Optional<Compte> compte = compteService.getCompte(id);
        if (compte.isPresent()) {
            return new ResponseEntity<>(compte.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Autorisation> newAutorisation(@PathVariable long id, @RequestBody Autorisation autorisation) {
        Optional<Autorisation> created = compteService.addAutorisation(id, autorisation);
        if (created.isPresent()) {
            return new ResponseEntity<>(created.get(),HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
