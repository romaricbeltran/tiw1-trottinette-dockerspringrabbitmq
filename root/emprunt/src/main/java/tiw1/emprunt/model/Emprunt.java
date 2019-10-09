package tiw1.emprunt.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "empruntById", query = "select e from Emprunt e where e.id = :id"),
        @NamedQuery(name = "allEmprunts", query = "select e from Emprunt e"),
        @NamedQuery(name = "empruntByDate", query = "select e from Emprunt e where e.date = :date")
})
public class Emprunt {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Long idAbonne, idTrottinette;

    public Emprunt() {
    }

    public Emprunt(Long id, Date date, Long idAbonne, Long idTrottinette) {
        this.id = id;
        this.date = date;
        this.idAbonne = idAbonne;
        this.idTrottinette = idTrottinette;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getIdAbonne() {
        return idAbonne;
    }

    public void setIdAbonne(Long idAbonne) {
        this.idAbonne = idAbonne;
    }

    public Long getIdTrottinette() {
        return idTrottinette;
    }

    public void setIdTrottinette(Long idTrottinette) {
        this.idTrottinette = idTrottinette;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunt emprunt = (Emprunt) o;
        return Objects.equals(id, emprunt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
