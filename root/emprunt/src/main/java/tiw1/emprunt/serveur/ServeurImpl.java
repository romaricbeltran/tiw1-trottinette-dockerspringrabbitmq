package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.Startable;
import org.picocontainer.behaviors.Caching;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.persistence.TrottinetteLoader;
import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;
import tiw1.emprunt.pooling.TrottinettePool;

import javax.json.*;
import javax.persistence.Persistence;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.picocontainer.Characteristics.CACHE;
import static tiw1.emprunt.annuaire.Sommaire.*;
import static tiw1.emprunt.persistence.TrottinetteLoader.getTrottinettes;

public class ServeurImpl implements Serveur {

    private static final String compagnie = "ELIM";
    private static Annuaire annuaire;

    public ServeurImpl(Annuaire annuaire) throws Exception {

        ServeurImpl.annuaire = annuaire;

        InputStream input = new FileInputStream("configurationApplication.json");
        JsonReader reader = Json.createReader(input);
        JsonObject jsonObject = reader.readObject();

        reader.close();
        input.close();

        JsonArray ressource_components = jsonObject.getJsonObject("application-config").getJsonArray("ressource-components");
        JsonArray persistence_components = jsonObject.getJsonObject("application-config").getJsonArray("persistence-components");

        DefaultPicoContainer conteneurRacine = new DefaultPicoContainer(new Caching());
        conteneurRacine.addComponent(annuaire);
        conteneurRacine.addComponent(COMPAGNIE, compagnie);
        conteneurRacine.addComponent("em", Persistence.createEntityManagerFactory("test-pu").createEntityManager());

        addClass(persistence_components, conteneurRacine);

        conteneurRacine.addComponent(TrottinettePool.class);

        addClass(ressource_components, conteneurRacine);

        conteneurRacine.addComponent(Controleur.class);

        annuaire.save(SERVEUR, this);
        annuaire.save(COMPAGNIE, conteneurRacine.getComponent(COMPAGNIE));
        annuaire.save(EM, conteneurRacine.getComponent("em"));
        annuaire.save(CONTROLEUR, conteneurRacine.getComponent(Controleur.class));

        annuaire.save(LISTE_TROTTINETTE, getTrottinettes());
        annuaire.save(TROTTINETTE_POOL, conteneurRacine.getComponent(TrottinettePool.class));
        
        saveClass(persistence_components, conteneurRacine);
        saveClass(ressource_components, conteneurRacine);

        ((Startable) annuaire.get(ABONNE_RESSOURCE)).start();
        ((Startable) annuaire.get(EMPRUNT_RESSOURCE)).start();
        ((Startable) annuaire.get(TROTTINETTE_RESSOURCE)).start();


        annuaire.addRessourceObservers("application/persistence");

        conteneurRacine.start();
    }

    @Override
    public Object processRequest(String commande, String methode, Map<String, Object> parametres) throws IOException, TrottinetteNonDisponibleException, TrottinetteNonRecupereException {
        return ((Controleur) annuaire.get(CONTROLEUR)).process(commande, methode, parametres);
    }

    public static Annuaire getAnnuaire() {
        return annuaire;
    }

    private void addClass(JsonArray jsonArray, DefaultPicoContainer conteneurRacine) throws Exception {
        for(var i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            conteneurRacine.as(CACHE).addComponent(Class.forName(jsonObject.getString("class-name")));
        }
    }

    private void saveClass(JsonArray jsonArray, DefaultPicoContainer conteneurRacine) throws Exception {
        for(var i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            annuaire.save(jsonArray.getJsonObject(i).getString("path"), conteneurRacine.getComponent(Class.forName(jsonObject.getString("class-name"))));
        }
    }
}
