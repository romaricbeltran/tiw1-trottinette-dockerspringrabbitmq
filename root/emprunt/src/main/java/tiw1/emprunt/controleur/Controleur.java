package tiw1.emprunt.controleur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.uniformisation.AbonneResource;
import tiw1.emprunt.uniformisation.EmpruntResource;
import tiw1.emprunt.uniformisation.TrottinetteResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controleur implements ControleurInterface {

    private static final Logger LOGGER_CONTROLEUR = LoggerFactory.getLogger(Controleur.class);
    private Map<String, ControleurInterface> controleursRessource = new HashMap<>();

    public Controleur() {
        controleursRessource.put("trottinette", new TrottinetteResource());
        controleursRessource.put("abonne", new AbonneResource());
        controleursRessource.put("emprunt", new EmpruntResource());
    }

    @Override
    public void start() {
        LOGGER_CONTROLEUR.info("Composant Controleur démarré. Objet d'accès aux données : " + this);
    }

    @Override
    public void stop() {
        LOGGER_CONTROLEUR.info("Composant Controleur arrêté");
    }

    @Override
    public Object process(String commande, String methode, Map<String, Object> parametres) throws IOException {
        return controleursRessource.get(commande).process(commande, methode, parametres);
    }
}
