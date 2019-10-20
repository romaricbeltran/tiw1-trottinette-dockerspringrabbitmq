package tiw1.emprunt.pooling;

import tiw1.emprunt.annuaire.Annuaire;
import tiw1.emprunt.model.Trottinette;

import java.util.Map;

import static tiw1.emprunt.annuaire.Sommaire.LISTE_TROTTINETTE;

public class TrottinettePool {

    private static Map<Long, Trottinette> trottinettes;

    private Annuaire annuaire;

    public TrottinettePool(Annuaire annuaire) {
        this.annuaire = annuaire;
        trottinettes = (Map<Long, Trottinette>) annuaire.get(LISTE_TROTTINETTE);
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
}
