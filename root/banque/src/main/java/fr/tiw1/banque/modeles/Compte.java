package fr.tiw1.banque.modeles;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Compte {
    @Id
    @GeneratedValue
    private Long id;

    private double valeur = 0.0;

    @OneToMany(mappedBy = "compte", fetch = FetchType.EAGER)
    private Collection<Autorisation> autorisations = new ArrayList<>();

    public Compte() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Collection<Autorisation> getAutorisations() {
        return autorisations;
    }

    public void setAutorisations(Collection<Autorisation> autorisations) {
        this.autorisations = autorisations;
    }

    public void addAutorisation(Autorisation autorisation) {
        autorisations.add(autorisation);
        autorisation.setCompte(this);
    }
}
