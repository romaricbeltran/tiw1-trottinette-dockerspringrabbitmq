package tiw1.emprunt.contexte;

import tiw1.emprunt.persistence.AbonneDAO;

public interface AbonneContexte {

    AbonneDAO getAbonneDAO();

    void setAbonneDAO(AbonneDAO abonneDAO);
}
