package fr.tiw1.banque.modeles;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@Entity
public class Autorisation {
    @Id
    @GeneratedValue
    private Long id;

    private double montant;

    private boolean used = false;

    @ManyToOne
    private Compte compte;

    public Autorisation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autorisation that = (Autorisation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @XmlTransient
    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    @Transient
    public long getCompteId() {
        return compte.getId();
    }

    public void setCompteId(long id) {
        // Do nothing as an authorisation should never change its account
    }
}
