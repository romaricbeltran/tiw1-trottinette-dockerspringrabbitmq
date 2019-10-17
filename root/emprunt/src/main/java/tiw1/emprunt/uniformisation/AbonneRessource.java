package tiw1.emprunt.uniformisation;

import tiw1.emprunt.contexte.AbonneContexte;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;

import java.io.IOException;
import java.util.Map;

public class AbonneRessource extends Ressource {

    private AbonneContexte abonneContexte;
    private AbonneDAO abonneDAO;

    public AbonneRessource(AbonneContexte abonneContexte) {
        this.abonneContexte = abonneContexte;
        abonneDAO = abonneContexte.getAbonneDAO();
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