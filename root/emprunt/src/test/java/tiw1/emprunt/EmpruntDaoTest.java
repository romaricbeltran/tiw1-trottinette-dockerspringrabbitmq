package tiw1.emprunt;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.annuaire.AnnuaireImpl;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.persistence.EmpruntDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static tiw1.emprunt.annuaire.Sommaire.EM;

public class EmpruntDaoTest {
    private EmpruntDAO dao;
    private Emprunt emprunt = new Emprunt(1L, new Date(), 1L, 1L);
    private static EntityManagerFactory emf;
    private EntityManager em;
    private Annuaire annuaire;

    @BeforeClass
    public static void setupEntityManager() {
        emf = Persistence.createEntityManagerFactory("test-pu");
    }

    @Before
    public void ajoutEmprunt() {
        em = emf.createEntityManager();
        annuaire = new AnnuaireImpl();
        annuaire.save(EM,em);
        dao = new EmpruntDAO(annuaire, em);
        dao.setEm(em);
        dao.save(emprunt);
    }

    @After
    public void supprimeEm() {
        em.close();
        em = null;
    }

    @Test
    public void testEmprunt() {
        assertEquals(emprunt, dao.get(emprunt.getId()).get());
    }

    @Test
    public void testListeEmprunt() {
        assertEquals(1, dao.getAll().size());
    }
}