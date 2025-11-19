package repository;

import model.Aluno;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Catálogo que armazena e gerencia alunos.
 */
public class CatalogoAluno implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Aluno> alunos;
    
    public CatalogoAluno() {
        this.alunos = new ArrayList<>();
    }
    
    /**
     * Adiciona um aluno ao catálogo.
     */
    public void addAluno(Aluno aluno) {
        alunos.add(aluno);
    }
    
    /**
     * Encontra um aluno pelo email.
     */
    public Aluno encontrarAlunoPorEmail(String email) {
        for (Aluno aluno : alunos) {
            if (aluno.getEmail().equalsIgnoreCase(email)) {
                return aluno;
            }
        }
        return null;
    }
    
    /**
     * Encontra um aluno pelo CPF.
     */
    public Aluno encontrarAlunoPorCpf(String cpf) {
        for (Aluno aluno : alunos) {
            if (aluno.getCpf().equals(cpf)) {
                return aluno;
            }
        }
        return null;
    }
    
    /**
     * Encontra um aluno pelo RA.
     */
    public Aluno encontrarAlunoPorRa(String ra) {
        for (Aluno aluno : alunos) {
            if (aluno.getRa().equals(ra)) {
                return aluno;
            }
        }
        return null;
    }
    
    /**
     * Retorna todos os alunos.
     */
    public List<Aluno> getTodosAlunos() {
        return new ArrayList<>(alunos);
    }
    
    /**
     * Retorna a quantidade de alunos cadastrados.
     */
    public int getTotalAlunos() {
        return alunos.size();
    }
}
