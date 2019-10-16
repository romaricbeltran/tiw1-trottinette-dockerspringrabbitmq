package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;
import tiw1.emprunt.contexte.AbonneContexte;
import tiw1.emprunt.contexte.AbonneContexteImpl;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;
import tiw1.emprunt.uniformisation.AbonneRessource;
import tiw1.emprunt.uniformisation.EmpruntRessource;
import tiw1.emprunt.uniformisation.TrottinetteRessource;

import java.io.IOException;
import java.util.Map;

import static org.picocontainer.Characteristics.CACHE;

public class ServeurImpl implements Serveur {

    private static final String NOM_COMPAGNIE = "ELIM";
    private Controleur controleur;
    private AbonneContexte contexte;

    public ServeurImpl() {
        DefaultPicoContainer conteneurRacine = new DefaultPicoContainer(new Caching());
        conteneurRacine.addComponent(AbonneContexte.class, AbonneContexteImpl.class);
        contexte = conteneurRacine.getComponent(AbonneContexte.class);
        conteneurRacine.addComponent(EmpruntDAO.class);
        conteneurRacine.addComponent(AbonneDAO.class);
        conteneurRacine.addComponent(TrottinetteLoader.class);
        conteneurRacine.addComponent("nomCompagnie", NOM_COMPAGNIE);

        conteneurRacine.as(CACHE).addComponent(AbonneRessource.class);
        conteneurRacine.as(CACHE).addComponent(EmpruntRessource.class);
        conteneurRacine.as(CACHE).addComponent(TrottinetteRessource.class);

        conteneurRacine.addComponent(Controleur.class);
        conteneurRacine.start();
        controleur = conteneurRacine.getComponent(Controleur.class);
    }

    @Override
    public Object processRequest(String commande, String methode, Map<String, Object> parametres) throws IOException {
        return controleur.process(commande, methode, parametres);
    }
}
