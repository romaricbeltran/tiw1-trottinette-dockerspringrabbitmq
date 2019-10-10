package tiw1.emprunt.dto;

import tiw1.emprunt.model.Emprunt;

import java.io.Serializable;
import java.util.Date;

public class EmpruntDTO implements Serializable {

    private Long id;
    private Date date;
    private Long idAbonne, idTrottinette;

    public EmpruntDTO() {
    }

    public EmpruntDTO(Long id, Date date, Long idAbonne, Long idTrottinette) {
        this.id = id;
        this.date = date;
        this.idAbonne = idAbonne;
        this.idTrottinette = idTrottinette;
    }

    public Emprunt createEmprunt() {
        return new Emprunt(id, date, idAbonne, idTrottinette);
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
}
