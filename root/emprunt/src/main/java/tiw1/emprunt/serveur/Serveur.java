package tiw1.emprunt.serveur;

import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;

import java.io.IOException;
import java.util.Map;

public interface Serveur {
    Object processRequest(String commande, String methode, Map<String, Object> parametres) throws IOException, TrottinetteNonDisponibleException, TrottinetteNonRecupereException;
}
