package catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Atividade;
import model.PAEG;

public class CatalogoPAEG {

    private List<PAEG> paegs;

    public CatalogoPAEG() {
        this.paegs = new ArrayList<>();
    }

    public boolean addPAEG(PAEG paeg) {
        if (paeg == null) {
            return false;
        }

        // Evitar duplicação por nome dentro do sistema (ou poderia ser por atividade)
        for (PAEG p : paegs) {
            if (p.getNome().equalsIgnoreCase(paeg.getNome())) {
                return false;
            }
        }

        paegs.add(paeg);
        return true;
    }

    public void excluir(PAEG paeg) {
        paegs.remove(paeg);
    }

    public List<PAEG> buscarPAEGsAbertos() {
        List<PAEG> abertos = new ArrayList<>();
        Date agora = new Date();

        for (PAEG paeg : paegs) {
            if (!agora.before(paeg.getDataInicialCandidatura())
                    && !agora.after(paeg.getDataFinalCandidatura())) {
                abertos.add(paeg);
            }
        }

        return abertos;
    }

    public List<PAEG> getPaegs() {
        return new ArrayList<>(paegs);
    }

    public List<PAEG> getPAEGsDaAtividade(Atividade atividade) {
        List<PAEG> resultado = new ArrayList<>();

        for (PAEG p : paegs) {
            Atividade a = p.getAtividade();

            boolean mesmoProjeto
                    = a.getProjeto().getNome().equalsIgnoreCase(atividade.getProjeto().getNome())
                    && a.getProjeto().getCriador().getCpf().equals(atividade.getProjeto().getCriador().getCpf());

            boolean mesmaAtividade
                    = a.getNome().equalsIgnoreCase(atividade.getNome());

            if (mesmaAtividade && mesmoProjeto) {
                resultado.add(p);
            }
        }

        return resultado;
    }

    public void setPaegs(List<PAEG> paegs) {
        this.paegs = paegs;
    }

}
