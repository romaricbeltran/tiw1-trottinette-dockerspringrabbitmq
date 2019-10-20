package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.Startable;
import org.picocontainer.behaviors.Caching;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;
import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;
import tiw1.emprunt.pooling.TrottinettePool;
import tiw1.emprunt.uniformisation.AbonneRessource;
import tiw1.emprunt.uniformisation.EmpruntRessource;
import tiw1.emprunt.uniformisation.TrottinetteRessource;

import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Map;

import static org.picocontainer.Characteristics.CACHE;
import static tiw1.emprunt.annuaire.Sommaire.*;
import static tiw1.emprunt.persistence.TrottinetteLoader.getTrottinettes;

public class ServeurImpl implements Serveur {

    private static final String compagnie = "ELIM";
    private static Annuaire annuaire;

    public ServeurImpl(Annuaire annuaire) {

        ServeurImpl.annuaire = annuaire;

        DefaultPicoContainer conteneurRacine = new DefaultPicoContainer(new Caching());
        conteneurRacine.addComponent(annuaire);
        conteneurRacine.addComponent(COMPAGNIE, compagnie);
        conteneurRacine.addComponent("em", Persistence.createEntityManagerFactory("test-pu").createEntityManager());

        conteneurRacine.addComponent(EmpruntDAO.class);
        conteneurRacine.addComponent(AbonneDAO.class);
        conteneurRacine.addComponent(TrottinetteLoader.class);

        conteneurRacine.addComponent(TrottinettePool.class);

        conteneurRacine.as(CACHE).addComponent(EmpruntRessource.class);
        conteneurRacine.as(CACHE).addComponent(AbonneRessource.class);
        conteneurRacine.as(CACHE).addComponent(TrottinetteRessource.class);

        conteneurRacine.addComponent(Controleur.class);

        annuaire.save(SERVEUR, this);
        annuaire.save(COMPAGNIE, conteneurRacine.getComponent(COMPAGNIE));
        annuaire.save(EM, conteneurRacine.getComponent("em"));
        annuaire.save(CONTROLEUR, conteneurRacine.getComponent(Controleur.class));

        annuaire.save(EMPRUNT_DAO, conteneurRacine.getComponent(EmpruntDAO.class));
        annuaire.save(ABONNE_DAO, conteneurRacine.getComponent(AbonneDAO.class));
        annuaire.save(TROTTINETTE_LOADER, conteneurRacine.getComponent(TrottinetteLoader.class));

        annuaire.save(ABONNE_RESSOURCE, conteneurRacine.getComponent(AbonneRessource.class));
        annuaire.save(EMPRUNT_RESSOURCE, conteneurRacine.getComponent(EmpruntRessource.class));
        annuaire.save(TROTTINETTE_RESSOURCE, conteneurRacine.getComponent(TrottinetteRessource.class));

        ((Startable) annuaire.get(ABONNE_RESSOURCE)).start();
        ((Startable) annuaire.get(EMPRUNT_RESSOURCE)).start();
        ((Startable) annuaire.get(TROTTINETTE_RESSOURCE)).start();

        annuaire.save(LISTE_TROTTINETTE, getTrottinettes());
        annuaire.save(TROTTINETTE_POOL, conteneurRacine.getComponent(TrottinettePool.class));

        annuaire.addRessourceObservers("application/persistence");

        // Test observer
        conteneurRacine.removeComponent(TrottinetteLoader.class);
        conteneurRacine.addComponent(TrottinetteLoader.class);
        annuaire.save(TROTTINETTE_LOADER, conteneurRacine.getComponent(TrottinetteLoader.class));
        ///

        conteneurRacine.start();
    }

    @Override
    public Object processRequest(String commande, String methode, Map<String, Object> parametres) throws IOException, TrottinetteNonDisponibleException, TrottinetteNonRecupereException {
        return ((Controleur) annuaire.get(CONTROLEUR)).process(commande, methode, parametres);
    }

    public static Annuaire getAnnuaire() {
        return annuaire;
    }
}
