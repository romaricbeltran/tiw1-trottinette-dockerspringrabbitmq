package tiw1.emprunt.annuaire;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.serveur.Serveur;
import tiw1.emprunt.serveur.ServeurImpl;

import static org.junit.Assert.assertEquals;
import static tiw1.emprunt.annuaire.Sommaire.ABONNE_DAO;

public class AnnuaireImplTest {

    private Annuaire annuaire;
    private Serveur serveur;

    @Before
    public void setUp() throws Exception {
        annuaire = new AnnuaireImpl();
        serveur = new ServeurImpl(annuaire);
    }

    @Test
    public void save() {
        AbonneDAO abonneDAO = new AbonneDAO();
        annuaire.save(ABONNE_DAO, abonneDAO);
        assertEquals(annuaire.get(ABONNE_DAO), abonneDAO);

        AbonneDAO abonneDAO1 = new AbonneDAO();

        annuaire.save(ABONNE_DAO, abonneDAO1);
        assertEquals(annuaire.get(ABONNE_DAO), abonneDAO1);
    }

    @Test
    public void addRessourceObservers() {
        Annuaire annuairebis = (AnnuaireImpl) annuaire.get("application/persistence");
        assertEquals(annuairebis.countObservers(), 3);
    }
}