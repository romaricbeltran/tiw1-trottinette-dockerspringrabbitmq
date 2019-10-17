package tiw1.emprunt.contexte;

import tiw1.emprunt.persistence.AbonneDAO;

public class AbonneContexteImpl implements AbonneContexte {

    private AbonneDAO abonneDAO;

    public AbonneContexteImpl(AbonneDAO abonneDAO) {
        this.abonneDAO = abonneDAO;
    }

    @Override
    public AbonneDAO getAbonneDAO() {
        return abonneDAO;
    }

    @Override
    public void setAbonneDAO(AbonneDAO abonneDAO) {
        this.abonneDAO = abonneDAO;
    }
}
