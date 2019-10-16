package tiw1.emprunt.serveur;

import java.io.IOException;
import java.util.Map;

public interface Serveur {
    Object processRequest(String commande, String methode, Map<String, Object> parametres) throws IOException;
}
