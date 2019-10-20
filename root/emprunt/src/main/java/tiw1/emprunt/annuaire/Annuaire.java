package tiw1.emprunt.annuaire;
//LOAADDD
public interface Annuaire {

    Object get(String commande);

    void save(String commande, Object objet);

    void addRessourceObservers(String commande);
}
