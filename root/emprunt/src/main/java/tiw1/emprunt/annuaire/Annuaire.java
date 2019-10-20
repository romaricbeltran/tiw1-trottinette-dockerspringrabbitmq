package tiw1.emprunt.annuaire;

public interface Annuaire {

    Object get(String commande);

    void save(String commande, Object objet);
}
