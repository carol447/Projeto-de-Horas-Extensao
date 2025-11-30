package repository;

import model.PAEG;

public class RepositorioPAEG extends RepositorioBase<PAEG> {

    public RepositorioPAEG() {
        super("dados/paegs.txt");
    }
}
