package tiw1.SpringBootTP3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiw1.SpringBootTP3.model.Trottinette;
import tiw1.SpringBootTP3.repository.TrottinetteRepository;

import java.util.Map;

@Component
public class TrottinetteServiceImpl implements TrottinetteService {

    @Autowired
    private TrottinetteRepository trottinetteRepository;

    private static final String MAINTENANCE_URL = "http://localhost:8080/trottinette/";

    private static Map<Long, Trottinette> trottinettes = null;
}
