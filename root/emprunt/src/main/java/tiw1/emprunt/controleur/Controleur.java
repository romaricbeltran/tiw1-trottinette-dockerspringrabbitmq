package tiw1.emprunt.controleur;

import org.modelmapper.ModelMapper;
import org.picocontainer.Startable;
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
import java.util.logging.Logger;

public class Controleur implements Startable {

    private Logger logger = Logger.getLogger("Controleur");
    private static final ModelMapper modelMapper = new ModelMapper();

    private TrottinetteLoader trottinetteLoader;
    private AbonneDAO abonneDAO;
    private EmpruntDAO empruntDAO;

    public Controleur() {
        trottinetteLoader = new TrottinetteLoader();
        try {
            abonneDAO = new AbonneDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        empruntDAO = new EmpruntDAO();
    }

    ///TODO Faut mettre en place le reflection pour scanner la dao utilisée
    @Override
    public void start() {
        logger.info("Composant Controleur démarré. Objet d'accès aux données : " + abonneDAO);
    }

    @Override
    public void stop() {
        logger.info("Composant Controleur arrêté");
    }

    // EmpruntDTO

    public EmpruntDTO getEmpruntDTO(Emprunt emprunt) {
        return modelMapper.map(emprunt, EmpruntDTO.class);
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

    // Getters/Setters

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public TrottinetteLoader getTrottinetteLoader() {
        return trottinetteLoader;
    }

    public void setTrottinetteLoader(TrottinetteLoader trottinetteLoader) {
        this.trottinetteLoader = trottinetteLoader;
    }

    public AbonneDAO getAbonneDAO() {
        return abonneDAO;
    }

    public void setAbonneDAO(AbonneDAO abonneDAO) {
        this.abonneDAO = abonneDAO;
    }

    public EmpruntDAO getEmpruntDAO() {
        return empruntDAO;
    }

    public void setEmpruntDAO(EmpruntDAO empruntDAO) {
        this.empruntDAO = empruntDAO;
    }
}
