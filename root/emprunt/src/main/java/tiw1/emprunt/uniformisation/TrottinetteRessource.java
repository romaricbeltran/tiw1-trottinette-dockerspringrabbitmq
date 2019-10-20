package tiw1.emprunt.uniformisation;

import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.util.Map;

public class TrottinetteRessource extends Ressource {

    public TrottinetteRessource(Annuaire annuaire) {
        super(annuaire);
    }

    //getTrottinetteDisponibilite
    @Override
    public Object get(Map<String, Object> parametres) {
        return TrottinetteLoader.getTrottinetteById((Long) parametres.get("id")).isDisponible();
    }

    @Override
    public Object getAll(Map<String, Object> parametres) {
        return null;
    }

    @Override
    public Object save(Map<String, Object> parametres) {
        return null;
    }

    @Override
    public Object delete(Map<String, Object> parametres) {
        return null;
    }

    @Override
    public void start() {
        try {
            TrottinetteLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
