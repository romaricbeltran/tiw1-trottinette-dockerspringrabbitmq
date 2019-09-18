package tiw1.maintenance.models;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "allBatteries", query = "SELECT b FROM Batterie b"),
        @NamedQuery(name = "batterieById", query = "SELECT b FROM Batterie b where b.id=:id"),
        @NamedQuery(name = "singleBatterieAvailable", query = "SELECT b FROM Batterie b where b.notInstall = true")
})

public class Batterie {

    @Id
    @GeneratedValue
    private Long id;

    private boolean notInstall = true;

    private boolean unPlugged = true;

    private boolean fullCharged = true;

    public Batterie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNotInstall() {
        return notInstall;
    }

    public void setNotInstall(boolean notInstall) {
        this.notInstall = notInstall;
    }

    public boolean isUnPlugged() {
        return unPlugged;
    }

    public void setUnPlugged(boolean unPlugged) {
        this.unPlugged = unPlugged;
    }

    public boolean isFullCharged() {
        return fullCharged;
    }

    public void setFullCharged(boolean fullCharged) {
        this.fullCharged = fullCharged;
    }
}
