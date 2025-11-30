package model;

import java.io.Serializable;

public class Aluno extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ra;
    private int horasAcumuladas;

    public Aluno(String nome, String cpf, String email, String senha, Cursos curso, String ra) {
        super(nome, cpf, email, senha, curso);
        this.ra = ra;
        this.horasAcumuladas = 0;  // começa zerado
    }

    public String getRa() {
        return ra;
    }

    public int getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public void setHorasAcumuladas(int horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    public void adicionarHorasACC(int horas) {
        if (horas > 0) {
            this.horasAcumuladas += horas;
        }
    }

    @Override
    public String toString() {
        return nome + " (" + email + ")";
    }
}
