package tiw1.emprunt.controleur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.contexte.Contexte;

import java.io.IOException;
import java.util.Map;

public class Controleur implements ControleurInterface {

    private static final Logger LOGGER_CONTROLEUR = LoggerFactory.getLogger(Controleur.class);
    private Contexte contexte;

    public Controleur(Contexte contexte) {
        this.contexte = contexte;
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
        return ((ControleurInterface) contexte.get(commande.substring(0, 1).toUpperCase()
                + commande.substring(1).toLowerCase()
                + "Ressource")).process(commande, methode, parametres);
    }
}
