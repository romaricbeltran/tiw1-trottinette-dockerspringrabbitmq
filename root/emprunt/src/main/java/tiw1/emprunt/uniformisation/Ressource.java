package tiw1.emprunt.uniformisation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.controleur.ControleurInterface;
import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;

import java.io.IOException;
import java.util.Map;
import java.util.Observer;

public abstract class Ressource implements ControleurInterface, Observer {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected Annuaire annuaire;

    public Ressource(Annuaire annuaire) {
        this.annuaire = annuaire;
    }

    @Override
    public Object process(String commande, String methode, Map<String, Object> parametres) throws IOException, TrottinetteNonDisponibleException, TrottinetteNonRecupereException {
        switch (methode) {
            case "get":
                return get(parametres);
            case "getAll":
                return getAll(parametres);
            case "save":
                return save(parametres);
            case "delete":
                return delete(parametres);
            case "getEmpruntByDate":
                EmpruntRessource empruntRessourceGEBD = new EmpruntRessource(annuaire);
                return empruntRessourceGEBD.getEmpruntByDate(parametres);
            case "getEmpruntDTO":
                EmpruntRessource empruntRessourceGEDTO = new EmpruntRessource(annuaire);
                return empruntRessourceGEDTO.getEmpruntDTO(parametres);
            case "saveEmpruntFromDTO":
                EmpruntRessource empruntRessourceSEFDTO = new EmpruntRessource(annuaire);
                return empruntRessourceSEFDTO.saveEmpruntFromDTO(parametres);
            case "recupererTrottinette":
                TrottinetteRessource trottinetteRessourceRecup = new TrottinetteRessource(annuaire);
                return trottinetteRessourceRecup.recupererTrottinette(parametres);
            case "rendreTrottinette":
                TrottinetteRessource trottinetteRessourceRendre = new TrottinetteRessource(annuaire);
                return trottinetteRessourceRendre.rendreTrottinette(parametres);
            default:
                return null;
        }
    }

    @Override
    public void start() {
        LOGGER.info("Composant Ressource démarrée. Objet d'accès aux données : " + this);
    }

    @Override
    public void stop() {
        LOGGER.info("Composant Ressource stoppé.");
    }

    public abstract Object get(Map<String, Object> parametres);

    public abstract Object getAll(Map<String, Object> parametres);

    public abstract Object save(Map<String, Object> parametres) throws IOException;

    public abstract Object delete(Map<String, Object> parametres) throws IOException;
}
