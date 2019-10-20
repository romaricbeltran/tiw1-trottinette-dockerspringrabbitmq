package tiw1.emprunt.uniformisation;

import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.persistence.TrottinetteLoader;
import tiw1.emprunt.pooling.TrottinetteNonDisponibleException;
import tiw1.emprunt.pooling.TrottinetteNonRecupereException;
import tiw1.emprunt.pooling.TrottinettePool;

import java.util.Map;
import java.util.Observable;

import static tiw1.emprunt.annuaire.Sommaire.TROTTINETTE_LOADER;
import static tiw1.emprunt.annuaire.Sommaire.TROTTINETTE_POOL;

public class TrottinetteRessource extends Ressource {

    private TrottinettePool trottinettePool;

    public TrottinetteRessource(Annuaire annuaire) {
        super(annuaire);
        trottinettePool = (TrottinettePool) annuaire.get(TROTTINETTE_POOL);
    }

    @Override
    public void update(Observable o, Object arg) {
        setTrottinettePool((TrottinettePool) annuaire.get(TROTTINETTE_POOL));
    }

    //getTrottinetteDisponibilite
    @Override
    public Object get(Map<String, Object> parametres) {
        return trottinettePool.getTrottinetteById((Long) parametres.get("id")).isDisponible();
    }

    protected Trottinette recupererTrottinette(Map<String, Object> parametres) throws TrottinetteNonDisponibleException {
        return trottinettePool.recupererTrottinette((long) parametres.get("id"));
    }

    protected Trottinette rendreTrottinette(Map<String, Object> parametres) throws TrottinetteNonRecupereException {
        return trottinettePool.rendreTrottinette((long) parametres.get("id"));
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

    public void setTrottinettePool(TrottinettePool trottinettePool) {
        this.trottinettePool = trottinettePool;
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
