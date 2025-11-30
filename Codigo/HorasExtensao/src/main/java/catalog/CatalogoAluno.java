package catalog;
import java.util.ArrayList;
import java.util.List;
import model.Aluno;

public class CatalogoAluno {

    private List<Aluno> alunos;

    public CatalogoAluno() {
        this.alunos = new ArrayList<>();
    }

    public boolean addAluno(Aluno aluno) {

        // Evita duplicação por CPF, RA ou e-mail
        if (encontrarAlunoPorCpf(aluno.getCpf()) != null) return false;
        if (encontrarAlunoPorRa(aluno.getRa()) != null) return false;
        if (encontrarAlunoPorEmail(aluno.getEmail()) != null) return false;

        alunos.add(aluno);
        return true;
    }


    public Aluno encontrarAlunoPorEmail(String email) {
        for (Aluno a : alunos) {
            if (a.getEmail().equalsIgnoreCase(email)) {
                return a;
            }
        }
        return null;
    }

    // ---------------------------------------------------------
    // encontrarAlunoPorCpf
    // ---------------------------------------------------------
    public Aluno encontrarAlunoPorCpf(String cpf) {
        for (Aluno a : alunos) {
            if (a.getCpf().equals(cpf)) {
                return a;
            }
        }
        return null;
    }


    public Aluno encontrarAlunoPorRa(String ra) {
        for (Aluno a : alunos) {
            if (a.getRa().equals(ra)) {
                return a;
            }
        }
        return null;
    }


    public void atualizar(Aluno alunoAtualizado) {

        for (int i = 0; i < alunos.size(); i++) {
            Aluno a = alunos.get(i);

            if (a.getCpf().equals(alunoAtualizado.getCpf())) {
                alunos.set(i, alunoAtualizado);
                return;
            }
        }
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
    
    
    public List<Aluno> getAlunos() {
        return alunos;
    }
}
