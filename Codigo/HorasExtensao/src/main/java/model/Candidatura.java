package model;

import java.io.Serializable;

public class Candidatura implements Serializable {

    private static final long serialVersionUID = 1L;

    private StatusCandidatura status;
    private final Aluno aluno;
    private final PAEG paeg;

    public Candidatura(Aluno aluno, PAEG paeg) {
        this.aluno = aluno;
        this.paeg = paeg;
        this.status = StatusCandidatura.PENDENTE;
    }

    public StatusCandidatura getStatus() {
        return status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public PAEG getPaeg() {
        return paeg;
    }

    public void setStatus(StatusCandidatura status) {
        this.status = status;
    }

    public boolean isPendente() {
        return status == StatusCandidatura.PENDENTE;
    }

    public boolean isAprovada() {
        return status == StatusCandidatura.APROVADO;
    }

    @Override
    public String toString() {
        return paeg.getNome() + " - Status: " + status;
    }
}
