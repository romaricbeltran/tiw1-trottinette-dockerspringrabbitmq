package tiw1.emprunt.controleur;

import org.modelmapper.ModelMapper;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.dto.EmpruntDTO;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Controleur implements Startable {

    private static final Logger LOGGER_CONTROLEUR = LoggerFactory.getLogger(Controleur.class);
    private static final ModelMapper modelMapper = new ModelMapper();

    private TrottinetteLoader trottinetteLoader;
    private AbonneDAO abonneDAO;
    private EmpruntDAO empruntDAO;

    public Controleur() throws Exception {
        trottinetteLoader = new TrottinetteLoader();
        TrottinetteLoader.load();
        abonneDAO = new AbonneDAO();
        empruntDAO = new EmpruntDAO();
    }

    @Override
    public void start() {
        LOGGER_CONTROLEUR.info("Composant Controleur démarré. Objet d'accès aux données : " + this);
    }

    @Override
    public void stop() {
        LOGGER_CONTROLEUR.info("Composant Controleur arrêté");
    }

    public Object process(String commande, Map<String, Object> parametres) throws IOException {
        switch (commande) {
            case "GetAbonne":
                return getAbonne((long) parametres.get("id"));
            case "GetAllAbonne":
                return getAllAbonne();
            case "SaveAbonne":
                saveAbonne((Abonne) parametres.get("abonne"));
                return null;
            case "DeleteAbonne":
                deleteAbonne((Abonne) parametres.get("abonne"));
                return null;

            case "GetEmprunt":
                return getEmprunt((long) parametres.get("id"));
            case "GetEmpruntByDate":
                return getEmpruntByDate((Date) parametres.get("date"));
            case "GetAllEmprunt":
                return getAllEmprunt();
            case "SaveEmprunt":
                saveEmprunt((Emprunt) parametres.get("emprunt"));
                return null;
            case "DeleteEmprunt":
                deleteEmprunt((Emprunt) parametres.get("emprunt"));
                return null;

            case "GetEmpruntDTO":
                return getEmpruntDTO((Emprunt) parametres.get("emprunt"));
            case "SaveEmpruntFromDTO":
                return saveEmpruntFromDTO((EmpruntDTO) parametres.get("empruntDTO"));

            case "GetTrottinetteDisponibilite":
                return getTrottinetteDisponibilite((long) parametres.get("id"));

            default:
                return null;
        }
    }

    // EmpruntDTO

    private EmpruntDTO getEmpruntDTO(Emprunt emprunt) {
        return modelMapper.map(emprunt, EmpruntDTO.class);
    }

    private Emprunt saveEmpruntFromDTO(EmpruntDTO empruntDTO) {
        return empruntDTO.createEmprunt();
    }

    // Trottinette

    private boolean getTrottinetteDisponibilite(long id) {
        return TrottinetteLoader.getTrottinetteById(id).isDisponible();
    }

    // Abonne

    private Optional getAbonne(long id) {
        return abonneDAO.get(id);
    }

    private List getAllAbonne() {
        return abonneDAO.getAll();
    }

    private void saveAbonne(Abonne abonne) throws IOException {
        abonneDAO.save(abonne);
    }

    private void updateAbonne(Abonne abonne) throws IOException {
        abonneDAO.update(abonne);
    }

    private void deleteAbonne(Abonne abonne) throws IOException {
        abonneDAO.delete(abonne);
    }

    // Emprunt

    private Optional getEmprunt(long id) {
        return empruntDAO.get(id);
    }

    private List getAllEmprunt() {
        return empruntDAO.getAll();
    }

    private void saveEmprunt(Emprunt emprunt) {
        empruntDAO.save(emprunt);
    }

    private void updateEmprunt(Emprunt emprunt) {
        empruntDAO.update(emprunt);
    }

    private void deleteEmprunt(Emprunt emprunt) {
        empruntDAO.delete(emprunt);
    }

    private List getEmpruntByDate(Date date) {
        return empruntDAO.getEmpruntByDate(date);
    }

    // Getters/Setters

    private TrottinetteLoader getTrottinetteLoader() {
        return trottinetteLoader;
    }

    private void setTrottinetteLoader(TrottinetteLoader trottinetteLoader) {
        this.trottinetteLoader = trottinetteLoader;
    }

    private AbonneDAO getAbonneDAO() {
        return abonneDAO;
    }

    private void setAbonneDAO(AbonneDAO abonneDAO) {
        this.abonneDAO = abonneDAO;
    }

    private EmpruntDAO getEmpruntDAO() {
        return empruntDAO;
    }

    private void setEmpruntDAO(EmpruntDAO empruntDAO) {
        this.empruntDAO = empruntDAO;
    }
}
