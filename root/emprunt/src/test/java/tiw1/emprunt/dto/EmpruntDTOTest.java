package tiw1.emprunt.dto;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.annuaire.AnnuaireImpl;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;
import tiw1.emprunt.serveur.Serveur;
import tiw1.emprunt.serveur.ServeurImpl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EmpruntDTOTest {

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
    public void createEmprunt() throws TrottinetteNonDisponibleException, IOException, TrottinetteNonRecupereException {

        // EmpruntDTO crée l'emprunt

        EmpruntDTO dto = new EmpruntDTO((long) 2, date, (long) 2, (long) 1);

        Map<String, Object> empruntDTO = new HashMap<>();
        empruntDTO.put("empruntDTO", dto);

        Emprunt emprunt = (Emprunt) serveur.processRequest("emprunt", "saveEmpruntFromDTO", empruntDTO);


        Map<String, Object> empruntFromDTO = new HashMap<>();
        empruntFromDTO.put("emprunt", emprunt);

        // On récupère le DTO de l'emprunt et on accède aux infos

        EmpruntDTO empruntDTOFromBase = (EmpruntDTO) serveur.processRequest("emprunt", "getEmpruntDTO", empruntFromDTO);

        assertEquals((long) 2, (long) empruntDTOFromBase.getId());
        assertEquals(date, empruntDTOFromBase.getDate());
        assertEquals((long) 2, (long) empruntDTOFromBase.getIdAbonne());
        assertEquals((long) 1, (long) empruntDTOFromBase.getIdTrottinette());

        // On supprime l'emprunt
        serveur.processRequest("emprunt", "delete", empruntFromDTO);
    }
}