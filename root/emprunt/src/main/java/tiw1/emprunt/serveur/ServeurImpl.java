package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;
import tiw1.emprunt.contexte.Contexte;
import tiw1.emprunt.contexte.ContexteImpl;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;
import tiw1.emprunt.uniformisation.AbonneRessource;
import tiw1.emprunt.uniformisation.EmpruntRessource;
import tiw1.emprunt.uniformisation.TrottinetteRessource;

import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Map;

import static org.picocontainer.Characteristics.CACHE;

public class ServeurImpl implements Serveur {

    private static final String NOM_COMPAGNIE = "ELIM";
    private Contexte contexte;

    public ServeurImpl() {
        DefaultPicoContainer conteneurRacine = new DefaultPicoContainer(new Caching());
        conteneurRacine.addComponent("nomCompagnie", NOM_COMPAGNIE);

        conteneurRacine.addComponent(Contexte.class, ContexteImpl.class);
        contexte = conteneurRacine.getComponent(Contexte.class);

        conteneurRacine.addComponent("em", Persistence.createEntityManagerFactory("test-pu").createEntityManager());
        contexte.save("em", conteneurRacine.getComponent("em"));

        conteneurRacine.addComponent(EmpruntDAO.class);
        conteneurRacine.addComponent(AbonneDAO.class);
        conteneurRacine.addComponent(TrottinetteLoader.class);

        conteneurRacine.as(CACHE).addComponent(EmpruntRessource.class);
        conteneurRacine.as(CACHE).addComponent(AbonneRessource.class);
        conteneurRacine.as(CACHE).addComponent(TrottinetteRessource.class);

        conteneurRacine.addComponent(Controleur.class);
        contexte.save("Controleur", conteneurRacine.getComponent(Controleur.class));
        conteneurRacine.start();
    }

    @Override
    public Object processRequest(String commande, String methode, Map<String, Object> parametres) throws IOException {
        return ((Controleur) contexte.get("Controleur")).process(commande, methode, parametres);
    }
}
