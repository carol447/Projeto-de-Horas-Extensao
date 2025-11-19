package model;

/**
 * Classe que representa um Professor no sistema.
 * Herda de Usuario.
 */
public class Professor extends Usuario {
    private static final long serialVersionUID = 1L;
    
    public Professor(String nome, String email, String senha, String cpf, String curso) {
        super(nome, email, senha, cpf, curso);
    }
    
    @Override
    public String toString() {
        return "Professor - " + super.toString();
    }
}
