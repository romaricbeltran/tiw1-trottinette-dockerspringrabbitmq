package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.io.IOException;
import java.util.Map;

public class ServeurImpl implements Serveur {

    private static final String NOM_COMPAGNIE = "ELIM";
    private static Controleur CONTROLEUR;

    public ServeurImpl() {
        DefaultPicoContainer conteneurRacine = new DefaultPicoContainer(new Caching());
        conteneurRacine.addComponent(EmpruntDAO.class);
        conteneurRacine.addComponent(AbonneDAO.class);
        conteneurRacine.addComponent(TrottinetteLoader.class);
        conteneurRacine.addComponent("nomCompagnie", NOM_COMPAGNIE);
        conteneurRacine.addComponent(Controleur.class);

        conteneurRacine.start();
        CONTROLEUR = conteneurRacine.getComponent(Controleur.class);
    }

    @Override
    public Object processRequest(String commande, Map<String, Object> parametres) throws IOException {
        return CONTROLEUR.process(commande, parametres);
    }
}
