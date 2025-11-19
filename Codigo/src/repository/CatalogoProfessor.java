package repository;

import model.Professor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Catálogo que armazena e gerencia professores.
 */
public class CatalogoProfessor implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Professor> professores;
    
    public CatalogoProfessor() {
        this.professores = new ArrayList<>();
    }
    
    /**
     * Adiciona um professor ao catálogo.
     */
    public void addProf(Professor professor) {
        professores.add(professor);
    }
    
    /**
     * Encontra um professor pelo email.
     */
    public Professor encontrarProfPorEmail(String email) {
        for (Professor prof : professores) {
            if (prof.getEmail().equalsIgnoreCase(email)) {
                return prof;
            }
        }
        return null;
    }
    
    /**
     * Encontra um professor pelo CPF.
     */
    public Professor encontrarProfPorCpf(String cpf) {
        for (Professor prof : professores) {
            if (prof.getCpf().equals(cpf)) {
                return prof;
            }
        }
        return null;
    }
    
    /**
     * Retorna todos os professores.
     */
    public List<Professor> getTodosProfessores() {
        return new ArrayList<>(professores);
    }
    
    /**
     * Retorna a quantidade de professores cadastrados.
     */
    public int getTotalProfessores() {
        return professores.size();
    }
}
