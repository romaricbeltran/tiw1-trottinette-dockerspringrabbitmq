package tiw1.SpringBootTP3.model;

import javax.persistence.*;
import java.util.List;

/**
 * Trottinette read-only à partir de la liste de trottinettes
 */
@Entity
public class Trottinette {

    @Id
    @GeneratedValue
    private long id;

    private boolean disponible = true;

    @OneToMany(targetEntity=Intervention.class, fetch=FetchType.EAGER)
    private List<Intervention> interventions;

    public Trottinette() {
    }

    public Trottinette(long id) {
        this.id = id;
    }

    public Trottinette(long id, boolean disponible) {
        this.id = id;
        this.disponible = disponible;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }

    public long getId() {
        return id;
    }

    public boolean isDisponible() {
        return disponible;
    }
}
