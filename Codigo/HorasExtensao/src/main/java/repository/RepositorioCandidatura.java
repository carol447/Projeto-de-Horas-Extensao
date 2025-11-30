package repository;

import model.Candidatura;

public class RepositorioCandidatura extends RepositorioBase<Candidatura> {

    public RepositorioCandidatura() {
        super("dados/candidaturas.txt");
    }
}
