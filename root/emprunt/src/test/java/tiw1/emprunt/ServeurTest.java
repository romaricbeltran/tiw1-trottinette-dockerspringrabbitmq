package tiw1.emprunt;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.serveur.Serveur;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServeurTest {

    private Serveur serveur;

    @Before
    public void setup() {
        // instanciation du serveur
        serveur = new Serveur();
    }

    @Test
    public void testGetTrottinetteDisponibilite() {
        System.out.println("testGetTrottinetteDisponibilite");
        assertFalse(serveur.getTrottinetteDisponibilite(1));
    }
}

