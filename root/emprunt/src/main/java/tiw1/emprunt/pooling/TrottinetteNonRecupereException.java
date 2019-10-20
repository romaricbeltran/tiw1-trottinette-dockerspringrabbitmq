package tiw1.emprunt.pooling;

public class TrottinetteNonRecupereException extends Exception {

    public TrottinetteNonRecupereException(Long id) {
        System.out.println("La trottinette que vous voulez rendre n'a pas été empruntée. " +
                "Peut-être vous seriez vous trompé d'identifiant ?");
        System.out.println("\t Trottinette => " + id);
    }
}
