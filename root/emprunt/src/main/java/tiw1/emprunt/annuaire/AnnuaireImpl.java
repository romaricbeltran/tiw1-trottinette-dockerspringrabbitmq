package tiw1.emprunt.annuaire;

import tiw1.emprunt.serveur.ServeurImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import static tiw1.emprunt.annuaire.Sommaire.*;

// Dans un annuaire JDNI, une association nom->objet est appelé un contexte. Object de la Map "contextes"
// pouvant lui même être un contexte, on supprime la classe Contexte qui contient une liste d'associations nom->objet
// et on construit l'annuaire par récursion en créant autant de sous-contexte que nécessaire.

public class AnnuaireImpl extends Observable implements Annuaire {

    private Map<String, Object> contextes = new HashMap<>();

    @Override
    public Object get(String commande) {

        if (commande.contains("/")) {
            Annuaire annuaire = (Annuaire) get(commande.substring(0, commande.indexOf("/")));
            return annuaire != null ? annuaire.get(commande.substring(commande.indexOf("/") + 1)) : null;
        } else {
            return contextes.get(commande);
        }
    }

    @Override
    public void save(String commande, Object objet) {

        if (commande.contains("/")) {
            String debut = commande.substring(0, commande.indexOf("/"));
            Annuaire annuaire = (AnnuaireImpl) contextes.get(debut);
            if (annuaire == null) {
                annuaire = new AnnuaireImpl();
                contextes.put(debut, annuaire);
            }
            annuaire.save(commande.substring(commande.indexOf("/") + 1), objet);
        } else if (contextes.containsKey(commande)) {
            contextes.remove(commande);
            contextes.put(commande, objet);
            setChanged();
            notifyObservers();
        } else {
            contextes.put(commande, objet);
        }
    }

    public void addRessourceObservers(String commande) {
        Annuaire annuaire = (Annuaire) get(commande);
        ((AnnuaireImpl) annuaire).addObserver((Observer) ServeurImpl.getAnnuaire().get(ABONNE_RESSOURCE));
        ((AnnuaireImpl) annuaire).addObserver((Observer) ServeurImpl.getAnnuaire().get(EMPRUNT_RESSOURCE));
        ((AnnuaireImpl) annuaire).addObserver((Observer) ServeurImpl.getAnnuaire().get(TROTTINETTE_RESSOURCE));
    }
}
