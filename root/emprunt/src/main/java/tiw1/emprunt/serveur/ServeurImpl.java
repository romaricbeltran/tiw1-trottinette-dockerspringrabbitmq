package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;
import tiw1.emprunt.uniformisation.AbonneResource;
import tiw1.emprunt.uniformisation.EmpruntResource;
import tiw1.emprunt.uniformisation.TrottinetteResource;

import java.io.IOException;
import java.util.Map;

public class ServeurImpl implements Serveur {

    private static final String NOM_COMPAGNIE = "ELIM";
    private Controleur controleur;

    //TODO : CACHE DES RESSOURCES + CONSTRUCTEUR DU CONTROLEUR AVEC LES RESSOURCES DU CONTAINER
    public ServeurImpl() {
        DefaultPicoContainer conteneurRacine = new DefaultPicoContainer(new Caching());
        conteneurRacine.addComponent(EmpruntDAO.class);
        conteneurRacine.addComponent(AbonneDAO.class);
        conteneurRacine.addComponent(TrottinetteLoader.class);
        conteneurRacine.addComponent("nomCompagnie", NOM_COMPAGNIE);

        conteneurRacine.addComponent(AbonneResource.class);
        conteneurRacine.addComponent(EmpruntResource.class);
        conteneurRacine.addComponent(TrottinetteResource.class);

        conteneurRacine.addComponent(Controleur.class);
        conteneurRacine.start();
        controleur = conteneurRacine.getComponent(Controleur.class);
    }

    @Override
    public Object processRequest(String commande, String methode, Map<String, Object> parametres) throws IOException {
        return controleur.process(commande, methode, parametres);
    }
}
