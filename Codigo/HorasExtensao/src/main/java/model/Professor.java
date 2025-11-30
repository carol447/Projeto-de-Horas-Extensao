package model;

import java.io.Serializable;

public class Professor extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    public Professor(String nome, String cpf, String email, String senha, Cursos curso) {
        super(nome, cpf, email, senha, curso);
    }

    @Override
    public String toString() {
        return nome + " (" + email + ")";
    }

}
