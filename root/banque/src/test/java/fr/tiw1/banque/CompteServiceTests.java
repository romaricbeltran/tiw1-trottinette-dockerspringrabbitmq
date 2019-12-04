package fr.tiw1.banque;

import fr.tiw1.banque.modeles.Autorisation;
import fr.tiw1.banque.modeles.Compte;
import fr.tiw1.banque.services.CompteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompteServiceTests {

    @Autowired
    private CompteService compteService;

    @Test
    public void testCreateCompte() {
        Compte c = new Compte();
        c.setValeur(3.0);
        c = compteService.createCompte(c);
        assertNotNull(c);
        assertNotNull(c.getId());
        assertEquals(0, c.getAutorisations().size());
    }

    @Test
    public void testGetComptes() {
        int nbInDb = compteService.allComptes().size();
        for (int i = 0; i < 5; i++) {
            Compte c = new Compte();
            c.setValeur((double) i);
            compteService.createCompte(c);
            assertNotNull(compteService.getCompte(c.getId()));
        }
        assertEquals(nbInDb + 5, compteService.allComptes().size());
    }

    @Test
    public void testAddAutorisation() {
        Compte c = compteService.createCompte(new Compte());
        Autorisation a = new Autorisation();
        a.setMontant(3.0);
        Optional<Autorisation> a2 = compteService.addAutorisation(c.getId(), a);
        assertTrue(a2.isPresent());
        c = compteService.getCompte(c.getId()).get();
        assertEquals(3.0, a2.get().getMontant(),0.0001);
        assertEquals(1, c.getAutorisations().size());
    }

    @Test
    @Transactional
    public void testTransfert() {
        Compte c1 = compteService.createCompte(new Compte());
        c1.setValeur(5.0);
        Compte c2 = compteService.createCompte(new Compte());
        c2.setValeur(10.0);
        Autorisation a = new Autorisation();
        a.setMontant(3.0);
        Optional<Autorisation> a2 = compteService.addAutorisation(c1.getId(), a);
        boolean ok = compteService.transfert(c1.getId(), c2.getId(), a.getId(), 3.0);
        assertTrue(ok);
        assertEquals(2.0, compteService.getCompte(c1.getId()).get().getValeur(), 0.0001);
        assertEquals(13.0, compteService.getCompte(c2.getId()).get().getValeur(), 0.0001);
    }
}
