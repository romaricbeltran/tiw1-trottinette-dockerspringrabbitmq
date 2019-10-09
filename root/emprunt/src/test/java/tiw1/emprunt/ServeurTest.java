package tiw1.emprunt;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.serveur.Serveur;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ServeurTest {

    private Serveur serveur;

    @Before
    public void setup() throws Exception {
        // Instanciation du serveur
        serveur = new Serveur();
    }

    @Test
    public void testServeurGetTrottinetteDisponibilite() {
        System.out.println("testServeurGetTrottinetteDisponibilite");
        assertFalse(serveur.getTrottinetteDisponibilite(1));
    }

    @Test
    public void testServeurAbonne() throws IOException {
        System.out.println("testServeurAbonne");

        List listAbonne = serveur.getAllAbonne();

        Abonne alice = new Abonne((long) 1, "Alice", new Date(), new Date());
        Abonne ben = new Abonne((long) 3, "Ben", new Date(), new Date());
        Abonne charles = new Abonne((long) 4, "Charles", new Date(), new Date());

        // Création
        System.out.println("testServeurAbonneCreation");

        serveur.saveAbonne(alice);
        serveur.saveAbonne(ben);
        serveur.saveAbonne(charles);
        assertEquals(4, listAbonne.size());

        // Suppression
        System.out.println("testServeurAbonneSuppression");

        serveur.deleteAbonne(alice);
        serveur.deleteAbonne(ben);
        serveur.deleteAbonne(charles);
        assertEquals(1, listAbonne.size());
    }
}

