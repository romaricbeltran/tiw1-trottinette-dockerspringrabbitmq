package tiw1.maintenance.models;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "allBatteries", query = "SELECT b FROM Batterie b"),
        @NamedQuery(name = "batterieById", query = "SELECT b FROM Batterie b where b.id=:id")
})
public class Batterie {

    @Id
    @GeneratedValue
    private Long id;

    public Batterie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
