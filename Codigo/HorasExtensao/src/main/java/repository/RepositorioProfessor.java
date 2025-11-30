package repository;

import model.Professor;

public class RepositorioProfessor extends RepositorioBase<Professor> {

    public RepositorioProfessor() {
        super("dados/professores.txt");
    }
}
