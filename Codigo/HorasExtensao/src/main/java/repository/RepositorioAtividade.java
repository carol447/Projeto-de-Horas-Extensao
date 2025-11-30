package repository;

import model.Atividade;

public class RepositorioAtividade extends RepositorioBase<Atividade> {

    public RepositorioAtividade() {
        super("dados/atividades.txt");
    }
}
