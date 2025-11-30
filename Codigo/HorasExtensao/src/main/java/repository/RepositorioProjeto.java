package repository;

import model.Projeto;

public class RepositorioProjeto extends RepositorioBase<Projeto> {

    public RepositorioProjeto() {
        super("dados/projetos.txt");
    }
}
