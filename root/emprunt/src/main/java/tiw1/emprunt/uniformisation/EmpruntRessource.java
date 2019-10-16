package tiw1.emprunt.uniformisation;

import org.modelmapper.ModelMapper;
import tiw1.emprunt.dto.EmpruntDTO;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class EmpruntRessource extends Ressource {

    private static final ModelMapper modelMapper = new ModelMapper();
    private EmpruntDAO empruntDAO;

    public EmpruntRessource() {
        empruntDAO = new EmpruntDAO();
    }

    protected List getEmpruntByDate(Map<String, Object> parametres) {
        return empruntDAO.getEmpruntByDate((Date) parametres.get("date"));
    }

    protected EmpruntDTO getEmpruntDTO(Map<String, Object> parametres) {
        return modelMapper.map((Emprunt) parametres.get("emprunt"), EmpruntDTO.class);
    }

    protected Emprunt saveEmpruntFromDTO(Map<String, Object> parametres) {
        EmpruntDTO dto = (EmpruntDTO) parametres.get("empruntDTO");
        return dto.createEmprunt();
    }

    @Override
    public Object get(Map<String, Object> parametres) {
        return empruntDAO.get((long) parametres.get("id"));
    }

    @Override
    public Object getAll(Map<String, Object> parametres) {
        return empruntDAO.getAll();
    }

    @Override
    public Object save(Map<String, Object> parametres) {
        empruntDAO.save((Emprunt) parametres.get("emprunt"));
        return null;
    }

    @Override
    public Object delete(Map<String, Object> parametres) {
        empruntDAO.delete((Emprunt) parametres.get("emprunt"));
        return null;
    }
}
