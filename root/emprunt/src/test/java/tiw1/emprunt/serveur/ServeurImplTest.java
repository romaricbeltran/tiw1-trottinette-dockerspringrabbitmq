package tiw1.emprunt.serveur;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.annuaire.AnnuaireImpl;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ServeurImplTest {

    private Annuaire annuaire;
    private Serveur serveur;
    private Date date;

    @Before
    public void setup() {
        annuaire = new AnnuaireImpl();
        serveur = new ServeurImpl(annuaire);
        date = new Date();
    }

    @Test
    public void testServeurGetTrottinetteDisponibilite() throws IOException, TrottinetteNonDisponibleException, TrottinetteNonRecupereException {
        System.out.println("testServeurGetTrottinetteDisponibilite");

        Map<String, Object> idTrottinette = new HashMap<>();
        idTrottinette.put("id", (long) 1);
        assertFalse((Boolean) serveur.processRequest("trottinette", "get", idTrottinette));
    }

    @Test
    public void testServeurAbonne() throws IOException, TrottinetteNonDisponibleException, TrottinetteNonRecupereException {
        System.out.println("testServeurAbonne");

        List listAbonne = (List) serveur.processRequest("abonne", "getAll", null);

        // Création
        System.out.println("testServeurAbonneCreation");

        Map<String, Object> alice = new HashMap<>();
        alice.put("abonne", new Abonne((long) 1, "Alice", date, date));

        Map<String, Object> ben = new HashMap<>();
        ben.put("abonne", new Abonne((long) 3, "Ben", date, date));

        Map<String, Object> charles = new HashMap<>();
        charles.put("abonne", new Abonne((long) 4, "Charles", date, date));

        serveur.processRequest("abonne", "save", alice);
        serveur.processRequest("abonne", "save", ben);
        serveur.processRequest("abonne", "save", charles);
        assertEquals(4, listAbonne.size());

        // Suppression
        System.out.println("testServeurAbonneSuppression");

        serveur.processRequest("abonne", "delete", alice);
        serveur.processRequest("abonne", "delete", ben);
        serveur.processRequest("abonne", "delete", charles);
        assertEquals(1, listAbonne.size());

        System.out.println("testServeurEmprunt");

        List<Emprunt> listEmprunts = new ArrayList<>();
        Emprunt emprunt1 = new Emprunt((long) 1, date, (long) 2, (long) 1);
        listEmprunts.add(emprunt1);

        // Création
        System.out.println("testServeurEmpruntCreation");

        Map<String, Object> emprunt = new HashMap<>();
        emprunt.put("emprunt", emprunt1);

        serveur.processRequest("emprunt", "save", emprunt);

        List listEmprunt = (List) serveur.processRequest("emprunt", "getAll", null);
        assertEquals(1, listEmprunt.size());


        // Récupération par date
        System.out.println("testServeurGetEmpruntByDate");

        Map<String, Object> dateMap = new HashMap<>();
        dateMap.put("date", date);

        List listEmpruntByDate = (List) serveur.processRequest("emprunt", "getEmpruntByDate", dateMap);
        assertEquals(listEmprunts, listEmpruntByDate);
    }
}
