package tiw1.emprunt.uniformisation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.controleur.ControleurInterface;

import java.io.IOException;
import java.util.Map;

public abstract class Ressource implements ControleurInterface {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public Object process(String commande, String methode, Map<String, Object> parametres) throws IOException {
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
                EmpruntResource empruntResourceGEBD = new EmpruntResource();
                return empruntResourceGEBD.getEmpruntByDate(parametres);
            case "getEmpruntDTO":
                EmpruntResource empruntResourceGEDTO = new EmpruntResource();
                return empruntResourceGEDTO.getEmpruntDTO(parametres);
            case "saveEmpruntFromDTO":
                EmpruntResource empruntResourceSEFDTO = new EmpruntResource();
                return empruntResourceSEFDTO.saveEmpruntFromDTO(parametres);
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
