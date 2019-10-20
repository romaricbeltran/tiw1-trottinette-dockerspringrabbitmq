package tiw1.emprunt.pooling;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.annuaire.AnnuaireImpl;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.persistence.TrottinetteLoader;
import tiw1.emprunt.serveur.Serveur;
import tiw1.emprunt.serveur.ServeurImpl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static tiw1.emprunt.annuaire.Sommaire.LISTE_TROTTINETTE;
import static tiw1.emprunt.annuaire.Sommaire.TROTTINETTE_LOADER;
import static tiw1.emprunt.annuaire.Sommaire.TROTTINETTE_POOL;

public class TrottinettePoolTest {

    private Annuaire annuaire;
    private Serveur serveur;
    private Date date;

    @Before
    public void setup() throws Exception {
        annuaire = new AnnuaireImpl();
        serveur = new ServeurImpl(annuaire);
        date = new Date();
    }

    @Test
    public void testTrottinettePool() throws IOException, TrottinetteNonDisponibleException, TrottinetteNonRecupereException {

        TrottinettePool trottinettePool = (TrottinettePool) annuaire.get(TROTTINETTE_POOL);
        Trottinette id1 = trottinettePool.getTrottinetteById(1);

        assertEquals(id1.getId(), 1);

        Map<String, Object> idTrottinette = new HashMap<>();
        idTrottinette.put("id", id1.getId());

        // Exception si on inverse les deux directives
        Trottinette trottinetteRendre = (Trottinette) serveur.processRequest("trottinette", "rendreTrottinette", idTrottinette);
        Trottinette trottinetteRecup = (Trottinette) serveur.processRequest("trottinette", "recupererTrottinette", idTrottinette);

        assertEquals(trottinetteRecup, trottinetteRendre);
    }
}