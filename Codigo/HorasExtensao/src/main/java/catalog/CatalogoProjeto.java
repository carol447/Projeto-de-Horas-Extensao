package catalog;

import java.util.ArrayList;
import java.util.List;
import model.Atividade;
import model.Professor;
import model.Projeto;

public class CatalogoProjeto {

    private List<Projeto> projetos;

    public CatalogoProjeto() {
        this.projetos = new ArrayList<>();
    }

    public boolean addProjeto(Projeto projeto) {
        if (projeto == null) {
            return false;
        }

        // Evitar duplicação por nome
        for (Projeto p : projetos) {
            if (p.getNome().equalsIgnoreCase(projeto.getNome())) {
                return false;
            }
        }

        projetos.add(projeto);
        return true;
    }


    public Projeto adicionarAtividade(Projeto projeto, Atividade atividade) {
        if (projeto == null || atividade == null) {
            return null;
        }

        projeto.adicionarAtividade(atividade);
        return projeto;
    }


    public List<Projeto> buscarPorMembro(Professor professor) {
    List<Projeto> resultado = new ArrayList<>();

    for (Projeto p : projetos) {
        if (p.isMembro(professor)) {
            resultado.add(p);
        }
    }

    return resultado;
}

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
}

