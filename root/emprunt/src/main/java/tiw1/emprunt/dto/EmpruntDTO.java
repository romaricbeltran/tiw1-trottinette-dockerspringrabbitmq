package tiw1.emprunt.dto;

import java.util.Date;

public class EmpruntDTO {
    private Long id;

    private Date date;

    private Long idAbonne, idTrottinette;

    public EmpruntDTO(Long idAbonne, Long idTrottinette) {
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
}
