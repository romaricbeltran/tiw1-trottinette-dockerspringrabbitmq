package tiw1.emprunt.annuaire;

public interface Sommaire {
    String SERVEUR = "serveur";
    String CONTROLEUR = "controleur";
    String COMPAGNIE = "compagnie";

    String ABONNE_RESSOURCE = "application/abonne";
    String EMPRUNT_RESSOURCE = "application/emprunt";
    String TROTTINETTE_RESSOURCE = "application/trottinette";

    String LISTE_TROTTINETTE = "application/metier/liste-trottinette";

    String ABONNE_DAO = "application/persistence/abonneDAO";
    String EMPRUNT_DAO = "application/persistence/empruntDAO";
    String TROTTINETTE_LOADER = "application/persistence/trottinetteLoader";

    String EM = "application/persistence/em";

    String TROTTINETTE_POOL = "application/persistence/trottinettePool";
}
