package catalog;

import java.util.ArrayList;
import java.util.List;
import model.Aluno;
import model.Candidatura;
import model.PAEG;

public class CatalogoCandidatura {

    private List<Candidatura> candidaturas;

    public CatalogoCandidatura() {
        this.candidaturas = new ArrayList<>();
    }

    // ======================================================
    // buscarPendentesPorPAEG
    // ======================================================
    public List<Candidatura> buscarPendentesPorPAEG(PAEG paeg) {
        List<Candidatura> resultado = new ArrayList<>();

        for (Candidatura c : candidaturas) {
            PAEG p = c.getPaeg();

            boolean mesmoPAEG
                    = p.getNome().equalsIgnoreCase(paeg.getNome())
                    && p.getAtividade().getNome().equalsIgnoreCase(paeg.getAtividade().getNome())
                    && p.getAtividade().getProjeto().getNome().equalsIgnoreCase(paeg.getAtividade().getProjeto().getNome())
                    && p.getAtividade().getProjeto().getCriador().getCpf().equals(paeg.getAtividade().getProjeto().getCriador().getCpf());

            if (mesmoPAEG && c.isPendente()) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    // ======================================================
    // contarAprovadasPorPAEG
    // ======================================================
    public int contarAprovadasPorPAEG(PAEG paeg) {
        int count = 0;

        for (Candidatura c : candidaturas) {
            if (c.getPaeg().equals(paeg) && c.isAprovada()) {
                count++;
            }
        }

        return count;
    }

    // ======================================================
    // atualizar
    // ======================================================
    public void atualizar(Candidatura candidatura) {
        // Como estamos usando objetos mutáveis, nada precisa ser feito.
        // Apenas mantemos o método pois o UML exige.
    }

    // ======================================================
    // contarCandidaturasPorPAEG
    // ======================================================
    public int contarCandidaturasPorPAEG(PAEG paeg) {
        int count = 0;

        for (Candidatura c : candidaturas) {
            if (c.getPaeg().equals(paeg)) {
                count++;
            }
        }
        return count;
    }

    // ======================================================
    // verificarCandidaturaExistente
    // ======================================================
    public void verificarCandidaturaExistente(PAEG paeg, Aluno aluno) {
        for (Candidatura c : candidaturas) {
            if (c.getAluno().equals(aluno) && c.getPaeg().equals(paeg)) {
                throw new IllegalStateException("Aluno já possui candidatura nesse PAEG.");
            }
        }
    }

    // ======================================================
    // addCandidatura
    // ======================================================
    public boolean addCandidatura(Candidatura candidatura) {
        if (candidatura == null) {
            return false;
        }

        candidaturas.add(candidatura);
        return true;
    }

    // ======================================================
    // buscarPorAluno
    // ======================================================
    public List<Candidatura> buscarPorAluno(Aluno aluno) {
        List<Candidatura> resultado = new ArrayList<>();

        for (Candidatura c : candidaturas) {
            if (c.getAluno().getCpf().equals(aluno.getCpf())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    // ======================================================
    // excluir
    // ======================================================
    public void excluir(Candidatura candidatura) {
        candidaturas.remove(candidatura);
    }

    public List<Candidatura> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(List<Candidatura> candidaturas) {
        this.candidaturas = candidaturas;
    }

    public List<Candidatura> buscarAprovadosPorPAEG(PAEG paeg) {
        List<Candidatura> resultado = new ArrayList<>();

        for (Candidatura c : candidaturas) {
            PAEG p = c.getPaeg();

            boolean mesmoPAEG
                    = p.getNome().equalsIgnoreCase(paeg.getNome())
                    && p.getAtividade().getNome().equalsIgnoreCase(paeg.getAtividade().getNome())
                    && p.getAtividade().getProjeto().getNome().equalsIgnoreCase(paeg.getAtividade().getProjeto().getNome())
                    && p.getAtividade().getProjeto().getCriador().getCpf()
                            .equals(paeg.getAtividade().getProjeto().getCriador().getCpf());

            if (mesmoPAEG && c.isAprovada()) {
                resultado.add(c);
            }
        }

        return resultado;
    }

}
