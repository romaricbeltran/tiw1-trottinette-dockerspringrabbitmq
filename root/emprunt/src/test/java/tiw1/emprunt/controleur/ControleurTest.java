package tiw1.emprunt.controleur;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.annuaire.AnnuaireImpl;
import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;
import tiw1.emprunt.serveur.Serveur;
import tiw1.emprunt.serveur.ServeurImpl;
import tiw1.emprunt.uniformisation.AbonneRessource;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static tiw1.emprunt.annuaire.Sommaire.ABONNE_RESSOURCE;

public class ControleurTest {

    private Annuaire annuaire;
    private Serveur serveur;

    @Before
    public void setUp() throws Exception {
        annuaire = new AnnuaireImpl();
        serveur = new ServeurImpl(annuaire);
    }

    @After
    public void tearDown() {
        ((Controleur) ServeurImpl.getAnnuaire().get("controleur")).stop();
    }

    @Test
    public void process() throws TrottinetteNonDisponibleException, IOException, TrottinetteNonRecupereException {
        AbonneRessource ressource = new AbonneRessource(annuaire);
        annuaire.save(ABONNE_RESSOURCE, ressource);
        AbonneRessource abonneRessource = (AbonneRessource) annuaire.get("application/abonne");
        assertEquals(abonneRessource, ressource);

        List listAbonne = (List) ((ControleurInterface) abonneRessource).process("abonne", "getAll", null);
        assertEquals(listAbonne.size(), 1);
    }
}