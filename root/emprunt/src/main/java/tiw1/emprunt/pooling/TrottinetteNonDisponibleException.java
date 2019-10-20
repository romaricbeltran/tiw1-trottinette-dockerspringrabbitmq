package tiw1.emprunt.pooling;

public class TrottinetteNonDisponibleException extends Exception {

    public TrottinetteNonDisponibleException(Long id) {
        System.out.println("La trottinette demandée n'est pas disponible.");
        System.out.println("\t Trottinette => " + id);
    }
}
