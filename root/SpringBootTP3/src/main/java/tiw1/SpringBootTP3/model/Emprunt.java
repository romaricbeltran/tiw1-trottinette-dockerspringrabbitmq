package tiw1.SpringBootTP3.model;

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

    private Boolean actif = false;

    public Emprunt() {
    }

    public Emprunt(Long idAbonne, Long idTrottinette) {
        this.date = new Date();
        this.idAbonne = idAbonne;
        this.idTrottinette = idTrottinette;
    }

    public Emprunt(Date date, Long idAbonne, Long idTrottinette) {
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

    public Boolean isActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
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
