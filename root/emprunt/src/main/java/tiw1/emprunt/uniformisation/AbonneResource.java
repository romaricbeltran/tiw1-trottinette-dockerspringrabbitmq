package tiw1.emprunt.uniformisation;

import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;

import java.io.IOException;
import java.util.Map;

public class AbonneResource extends Ressource {

    private AbonneDAO abonneDAO;

    public AbonneResource() {
        abonneDAO = new AbonneDAO();
    }

    @Override
    public Object get(Map<String, Object> parametres) {
        return abonneDAO.get((long) parametres.get("id"));
    }

    @Override
    public Object getAll(Map<String, Object> parametres) {
        return abonneDAO.getAll();
    }

    @Override
    public Object save(Map<String, Object> parametres) throws IOException {
        abonneDAO.save((Abonne) parametres.get("abonne"));
        return null;
    }

    @Override
    public Object delete(Map<String, Object> parametres) throws IOException {
        abonneDAO.delete((Abonne) parametres.get("abonne"));
        return null;
    }
}