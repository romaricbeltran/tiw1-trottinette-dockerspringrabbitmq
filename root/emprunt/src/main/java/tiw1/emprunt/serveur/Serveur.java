package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.dto.EmpruntDTO;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Serveur {

    private static Controleur CONTROLEUR;

    private static final String NOM_COMPAGNIE = "ELIM";


    private DefaultPicoContainer conteneurRacine;

    public Serveur() {
        conteneurRacine = new DefaultPicoContainer(new Caching());
        conteneurRacine.addComponent(EmpruntDAO.class);
        conteneurRacine.addComponent(AbonneDAO.class);
        conteneurRacine.addComponent(TrottinetteLoader.class);
        conteneurRacine.addComponent(NOM_COMPAGNIE);
        conteneurRacine.addComponent(Controleur.class);

        CONTROLEUR = conteneurRacine.getComponent(Controleur.class);

        CONTROLEUR.start();
    }

    public Controleur getControleur() {
        return conteneurRacine.getComponent(Controleur.class);
    }

    // EmpruntDTO

    public EmpruntDTO getEmpruntDTO(Emprunt emprunt) {
        return getControleur().getEmpruntDTO(emprunt);
    }

    // Trottinette

    public boolean getTrottinetteDisponibilite(long id) {
        return getControleur().getTrottinetteDisponibilite(id);
    }

    // Abonne

    public List getAllAbonne() {
        return getControleur().getAllAbonne();
    }

    public void saveAbonne(Abonne abonne) throws IOException {
        getControleur().saveAbonne(abonne);
    }

    public void updateAbonne(Abonne abonne) throws IOException {
        getControleur().updateAbonne(abonne);
    }

    public void deleteAbonne(Abonne abonne) throws IOException {
        getControleur().deleteAbonne(abonne);
    }

    // Emprunt

    public List getAllEmprunt() {
        return getControleur().getAllEmprunt();
    }

    public void saveEmprunt(Emprunt emprunt) {
        getControleur().saveEmprunt(emprunt);
    }

    public void updateEmprunt(Emprunt emprunt) {
        getControleur().updateEmprunt(emprunt);
    }

    public void deleteEmprunt(Emprunt emprunt) {
        getControleur().deleteEmprunt(emprunt);
    }

    public List getEmpruntByDate(Date date) {
        return getControleur().getEmpruntByDate(date);
    }

    // Getters/Setters

    public static Controleur getCONTROLEUR() {
        return CONTROLEUR;
    }

    public static void setCONTROLEUR(Controleur CONTROLEUR) {
        Serveur.CONTROLEUR = CONTROLEUR;
    }

    public static String getNomCompagnie() {
        return NOM_COMPAGNIE;
    }

    public DefaultPicoContainer getConteneurRacine() {
        return conteneurRacine;
    }

    public void setConteneurRacine(DefaultPicoContainer conteneurRacine) {
        this.conteneurRacine = conteneurRacine;
    }
}
