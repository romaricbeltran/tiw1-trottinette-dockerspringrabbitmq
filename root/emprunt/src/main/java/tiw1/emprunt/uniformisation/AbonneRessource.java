package tiw1.emprunt.uniformisation;

import tiw1.emprunt.contexte.Contexte;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.io.IOException;
import java.util.Map;

public class AbonneRessource extends Ressource {

    private AbonneDAO abonneDAO;

    public AbonneRessource(Contexte contexte) {
        super(contexte);
        abonneDAO = (AbonneDAO) contexte.get(AbonneDAO.class.getSimpleName());
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