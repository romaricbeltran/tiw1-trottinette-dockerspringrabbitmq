package tiw1.emprunt.annuaire;

import java.util.HashMap;
import java.util.Map;

// Dans un annuaire JDNI, une association nom->objet est appelé un contexte. Object de la Map "contextes"
// pouvant lui même être un contexte, on supprime la classe Contexte qui contient une liste d'associations nom->objet
// et on construit l'annuaire par récursion en créant autant de sous-contexte que nécessaire.

public class AnnuaireImpl implements Annuaire {

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
        } else {
            contextes.put(commande, objet);
        }
    }
}
