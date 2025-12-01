package catalog;

import java.util.ArrayList;
import java.util.List;
import model.Professor;

public class CatalogoProfessor {

    private List<Professor> professores;

    public CatalogoProfessor() {
        this.professores = new ArrayList<>();
    }

    public boolean addProf(Professor prof) {
        if (prof == null) {
            return false;
        }
        
        if (encontrarProfPorCpf(prof.getCpf()) != null) {
            return false;
        }
        
        if (encontrarProfPorEmail(prof.getEmail()) != null) {
            return false;
        }

        professores.add(prof);
        return true;
    }

    public Professor encontrarProfPorEmail(String email) {
        for (Professor prof : professores) {
            if (prof.getEmail().equalsIgnoreCase(email)) {
                return prof;
            }
        }
        return null;
    }

    public Professor encontrarProfPorCpf(String cpf) {
        for (Professor prof : professores) {
            if (prof.getCpf().equals(cpf)) {
                return prof;
            }
        }
        return null;
    }
    
   
    

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }
}
