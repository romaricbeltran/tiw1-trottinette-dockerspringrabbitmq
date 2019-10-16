package tiw1.emprunt.controleur;

import org.picocontainer.Startable;

import java.io.IOException;
import java.util.Map;

public interface ControleurInterface extends Startable {
    Object process(String commande, String methode, Map<String, Object> parametres) throws IOException;
}