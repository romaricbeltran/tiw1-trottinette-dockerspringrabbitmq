package tiw1.emprunt.controleur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;

import java.io.IOException;
import java.util.Map;

public class Controleur implements ControleurInterface {

    private static final Logger LOGGER_CONTROLEUR = LoggerFactory.getLogger(Controleur.class);
    private Annuaire annuaire;

    public Controleur(Annuaire annuaire) {
        this.annuaire = annuaire;
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
    public Object process(String commande, String methode, Map<String, Object> parametres) throws IOException, TrottinetteNonDisponibleException, TrottinetteNonRecupereException {
        return ((ControleurInterface) annuaire.get("application/" + commande)).process(commande, methode, parametres);
    }
}
