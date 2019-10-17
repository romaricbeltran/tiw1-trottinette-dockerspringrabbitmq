package tiw1.emprunt.contexte;

public interface Contexte {

    Object get(String name);

    void save(String name, Object object);
}
