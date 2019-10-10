package tiw1.emprunt.serveur;

import org.modelmapper.ModelMapper;
import tiw1.emprunt.dto.EmpruntDTO;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Serveur {

    private AbonneDAO abonneDAO;
    private EmpruntDAO empruntDAO;

    private static final ModelMapper modelMapper = new ModelMapper();


    public Serveur() throws Exception {
        TrottinetteLoader.load();
        abonneDAO = new AbonneDAO();
        empruntDAO = new EmpruntDAO();
    }

    // Trottinette

    public boolean getTrottinetteDisponibilite(long id) {
        return TrottinetteLoader.getTrottinetteById(id).isDisponible();
    }

    // Abonne

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

    // Emprunt

    public Optional getEmprunt(long id) {
        return empruntDAO.get(id);
    }

    public List getAllEmprunt() {
        return empruntDAO.getAll();
    }

    public void saveEmprunt(Emprunt emprunt) {
        empruntDAO.save(emprunt);
    }

    public void updateEmprunt(Emprunt emprunt) {
        empruntDAO.update(emprunt);
    }

    public void deleteEmprunt(Emprunt emprunt) {
        empruntDAO.delete(emprunt);
    }

    public List getEmpruntByDate(Date date) {
        return empruntDAO.getEmpruntByDate(date);
    }

    // EmpruntDTO

    public EmpruntDTO getEmpruntDTO(Emprunt emprunt) {
        return modelMapper.map(emprunt, EmpruntDTO.class);
    }
}
