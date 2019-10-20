package tiw1.emprunt.persistence;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.annuaire.AnnuaireImpl;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

public class AbonneDAOTest {
    private AbonneDAO dao = null;

    @Before
    public void initDao() {
        dao = new AbonneDAO();
        dao.start();
    }

    @Test
    public void testDaoCreation() {
        System.out.println("testDaoCreation");
        assertNotNull(dao);
    }

    @Test
    public void testAbonneCreation() {
        System.out.println("testAbonneCreation");
        Abonne a = new Abonne(new Long(1), "Toto", new Date(), new Date());
        Abonne b = new Abonne(new Long(2), "Tata", new Date(), new Date());
        try {
            dao.save(a);
            dao.save(b);
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(2, dao.getAll().size());
    }

    @Test
    public void testAbonneSuppression() {
        System.out.println("testAbonneSuppression");
        Abonne a = new Abonne(new Long(1), "Titi", new Date(), new Date());
        try {
            dao.delete(a);
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(1, dao.getAll().size());
    }

    @Test
    public void testAbonneUpdate() {
        System.out.println("testAbonneUpdate");
        Abonne a = new Abonne(2L, "Tutu", new Date(), new Date());
        try {
            dao.update(a);
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(a, dao.get(2).get());
    }
}