package tiw1.emprunt.controleur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.contexte.AbonneContexte;
import tiw1.emprunt.uniformisation.AbonneRessource;
import tiw1.emprunt.uniformisation.EmpruntRessource;
import tiw1.emprunt.uniformisation.TrottinetteRessource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controleur implements ControleurInterface {

    private static final Logger LOGGER_CONTROLEUR = LoggerFactory.getLogger(Controleur.class);
    private Map<String, Object> objectsRessource = new HashMap<>();
    private AbonneContexte abonneContexte;

    public Controleur(TrottinetteRessource trottinetteRessource, AbonneContexte abonneContexte, EmpruntRessource empruntRessource) {
        objectsRessource.put("trottinette", trottinetteRessource);
        objectsRessource.put("abonne", abonneContexte);
        objectsRessource.put("emprunt", empruntRessource);
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
        if (!commande.equals("abonne")) {
            ControleurInterface controleurInterface = (ControleurInterface) objectsRessource.get(commande);
            return controleurInterface.process(commande, methode, parametres);
        } else {
            abonneContexte = (AbonneContexte) objectsRessource.get(commande);
            AbonneRessource abonneRessource = new AbonneRessource(abonneContexte);
            ControleurInterface abonneControleurInterface = (ControleurInterface) abonneRessource;
            return abonneControleurInterface.process(commande, methode, parametres);
        }
    }
}
