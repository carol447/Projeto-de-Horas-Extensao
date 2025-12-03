package catalog;

import java.util.ArrayList;
import java.util.List;
import model.Atividade;
import model.PAEG;
import model.Professor;
import model.Projeto;

public class CatalogoAtividade {

    private List<Atividade> atividades;

    public CatalogoAtividade() {
        this.atividades = new ArrayList<>();
    }

    public boolean addAtividade(Atividade atividade) {
        if (atividade == null) {
            return false;
        }

        // Evita duplicação pelo nome da atividade
        for (Atividade a : atividades) {
            if (a.getNome().equalsIgnoreCase(atividade.getNome())) {
                return false;
            }
        }

        atividades.add(atividade);
        return true;
    }

    public boolean adicionarPAEG(Atividade atividade, PAEG paeg) {
        if (atividade == null || paeg == null) {
            return false;
        }
        return atividade.adicionarPAEG(paeg);
    }

    public void excluir(Atividade atividade) {
        atividades.remove(atividade);
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public List<Atividade> getAtividadesDoProjeto(Projeto projeto) {
        List<Atividade> resultado = new ArrayList<>();
        for (Atividade a : atividades) {
            if (a.getProjeto().getCriador().getCpf().equals(projeto.getCriador().getCpf())
                    && a.getProjeto().getNome().equalsIgnoreCase(projeto.getNome())) {
                resultado.add(a);
            }

        }
        return resultado;
    }

    public List<Atividade> getAtividadesDoProfessor(Professor professor) {
        List<Atividade> resultado = new ArrayList<>();
        for (Atividade a : atividades) {
            if (a.getProjeto().getCriador().equals(professor)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
}
