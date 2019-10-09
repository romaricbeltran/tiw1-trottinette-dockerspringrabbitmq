package tiw1.emprunt.serveur;

import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Serveur {

    private AbonneDAO abonneDAO;

    public Serveur() throws Exception {
        TrottinetteLoader.load();
        abonneDAO = new AbonneDAO();
    }

    public boolean getTrottinetteDisponibilite(long id) {
        return TrottinetteLoader.getTrottinetteById(id).isDisponible();
    }

    public Optional getAbonne(long id) {
        return abonneDAO.get(id);
    }

    public List getAllAbonne() {
        return abonneDAO.getAll();
    }

    public void saveAbonne(Abonne abonne) throws IOException {
        abonneDAO.save(abonne);
    }

    public void updateAbonne(Abonne abonne) throws IOException {
        abonneDAO.update(abonne);
    }

    public void deleteAbonne(Abonne abonne) throws IOException {
        abonneDAO.delete(abonne);
    }
}
