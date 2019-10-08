package tiw1.emprunt.serveur;

import tiw1.emprunt.persistence.TrottinetteLoader;

public class Serveur {

    public Serveur() {
        try {
            TrottinetteLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getTrottinetteDisponibilite(long id) {
        return TrottinetteLoader.getTrottinetteById(id).isDisponible();
    }
}
