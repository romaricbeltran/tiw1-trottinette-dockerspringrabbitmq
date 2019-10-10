package tiw1.emprunt;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.dto.EmpruntDTO;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.serveur.Serveur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ServeurTest {

    private Serveur serveur;
    private Date date;

    @Before
    public void setup() throws Exception {
        // Instanciation du serveur
        serveur = new Serveur();
        date = new Date();
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

    @Test
    public void testServeurEmprunt() {
        System.out.println("testServeurEmprunt");

        List<Emprunt> emprunts = new ArrayList<>();
        Emprunt emprunt1 = new Emprunt((long) 1, date, (long) 2, (long) 1);
        emprunts.add(emprunt1);

        // Création
        System.out.println("testServeurEmpruntCreation");

        serveur.saveEmprunt(emprunt1);
        List listEmprunt = serveur.getAllEmprunt();
        assertEquals(1, listEmprunt.size());


        // Récupération par date
        System.out.println("testServeurGetEmpruntByDate");

        List listEmpruntByDate = serveur.getEmpruntByDate(date);
        assertEquals(emprunts, listEmpruntByDate);
    }

    @Test
    public void testEmpruntDTO() {

        // EmpruntDTO crée l'emprunt

        EmpruntDTO empruntDTO = new EmpruntDTO((long) 2, date, (long) 2, (long) 1);
        serveur.saveEmprunt(empruntDTO.createEmprunt());

        Emprunt empruntTestSave = empruntDTO.createEmprunt();

        // On récupère le DTO de l'emprunt et on accède aux infos

        EmpruntDTO empruntDTO1 = serveur.getEmpruntDTO(empruntTestSave);

        assertEquals(empruntDTO.getId(), empruntDTO1.getId());
        assertEquals(empruntDTO.getDate(), empruntDTO1.getDate());
        assertEquals(empruntDTO.getIdAbonne(), empruntDTO1.getIdAbonne());
        assertEquals(empruntDTO.getIdTrottinette(), empruntDTO1.getIdTrottinette());

        serveur.deleteEmprunt(empruntTestSave);
    }
}
