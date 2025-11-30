package repository;

import model.Aluno;

public class RepositorioAluno extends RepositorioBase<Aluno> {

    public RepositorioAluno() {
        super("dados/alunos.txt");
    }
    
}