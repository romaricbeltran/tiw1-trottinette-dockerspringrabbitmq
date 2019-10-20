package tiw1.emprunt.uniformisation;

import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.util.Map;
import java.util.Observable;

import static tiw1.emprunt.annuaire.Sommaire.TROTTINETTE_LOADER;

public class TrottinetteRessource extends Ressource {

    private TrottinetteLoader trottinetteLoader;

    public TrottinetteRessource(Annuaire annuaire) {
        super(annuaire);
        trottinetteLoader = (TrottinetteLoader) annuaire.get(TROTTINETTE_LOADER);
    }

    @Override
    public void update(Observable o, Object arg) {
        setTrottinetteLoader((TrottinetteLoader) annuaire.get(TROTTINETTE_LOADER));
    }

    //getTrottinetteDisponibilite
    @Override
    public Object get(Map<String, Object> parametres) {
        return trottinetteLoader.getTrottinetteById((Long) parametres.get("id")).isDisponible();
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

    public void setTrottinetteLoader(TrottinetteLoader trottinetteLoader) {
        this.trottinetteLoader = trottinetteLoader;
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
