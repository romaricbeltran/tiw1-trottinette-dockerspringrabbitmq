package tiw1.emprunt.pooling;

import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.model.Trottinette;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import static tiw1.emprunt.annuaire.Sommaire.LISTE_TROTTINETTE;

public class TrottinettePool implements Observer {

    private static Map<Long, Trottinette> trottinettes;
    private Annuaire annuaire;

    public TrottinettePool(Annuaire annuaire) {
        this.annuaire = annuaire;
        trottinettes = (Map<Long, Trottinette>) annuaire.get(LISTE_TROTTINETTE);
    }

    @Override
    public void update(Observable o, Object arg) {
        setTrottinettes((Map<Long, Trottinette>) annuaire.get(LISTE_TROTTINETTE));
    }

    public Trottinette recupererTrottinette(long id) throws TrottinetteNonDisponibleException {
        Trottinette trottinette = trottinettes.get(id);
        if (!trottinette.isDisponible()) {
            throw new TrottinetteNonDisponibleException(id);
        }
        trottinette.setDisponible(false);
        return trottinette;
    }

    public Trottinette rendreTrottinette(long id) throws TrottinetteNonRecupereException {
        Trottinette trottinette = trottinettes.get(id);
        if (trottinette.isDisponible()) {
            throw new TrottinetteNonRecupereException(id);
        }
        trottinette.setDisponible(true);
        return trottinette;
    }

    public Trottinette getTrottinetteById(long id) {
        return trottinettes.get(id);
    }

    private static void setTrottinettes(Map<Long, Trottinette> trottinettes) {
        TrottinettePool.trottinettes = trottinettes;
    }
}

