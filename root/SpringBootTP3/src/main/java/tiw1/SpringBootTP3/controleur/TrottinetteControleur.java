package tiw1.SpringBootTP3.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tiw1.SpringBootTP3.model.Trottinette;
import tiw1.SpringBootTP3.repository.TrottinetteRepository;

import java.util.Optional;

@RestController
public class TrottinetteControleur {

    @Autowired
    private TrottinetteRepository trottinetteRepository;

    @GetMapping(value = "/trottinette")
    public MappingJacksonValue listeTrottinettes() {
        System.out.println("TROTTIENEETETETE          " + trottinetteRepository.findAll());
        return new MappingJacksonValue(trottinetteRepository.findAll());
    }

    @GetMapping(value = "/trottinette/{id}")
    public Optional<Trottinette> getTrottinette(@PathVariable long id) {
        return trottinetteRepository.findById(id);
    }
}
