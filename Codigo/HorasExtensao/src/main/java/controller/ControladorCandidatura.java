package controller;

import java.util.List;

import model.Aluno;
import model.Candidatura;
import catalog.CatalogoAluno;
import catalog.CatalogoCandidatura;
import catalog.CatalogoNotificacao;
import model.Notificacao;
import model.PAEG;
import model.Professor;
import model.StatusCandidatura;

public class ControladorCandidatura {

    private final CatalogoCandidatura catalogoCandidatura;
    private final ControladorProjeto controladorProjeto;
    private final CatalogoAluno catalogoAluno;
    private final CatalogoNotificacao catalogoNotificacao;

    public ControladorCandidatura(CatalogoCandidatura catalogoCandidatura,
                                  ControladorProjeto controladorProjeto,
                                  CatalogoAluno catalogoAluno,
                                  CatalogoNotificacao catalogoNotificacao) {
        this.catalogoCandidatura = catalogoCandidatura;
        this.controladorProjeto = controladorProjeto;
        this.catalogoAluno = catalogoAluno;
        this.catalogoNotificacao = catalogoNotificacao;
    }

    // ======================================================
    // visualizarCandidaturasPendentes
    // ======================================================
    public List<Candidatura> visualizarCandidaturasPendentes(PAEG paeg, Professor professor) {

        // Apenas membro do projeto acessa
        if (!controladorProjeto.verificarProfessorMembro(professor, paeg.getAtividade().getProjeto())) {
            return List.of();
        }

        return catalogoCandidatura.buscarPendentesPorPAEG(paeg);
    }

    // ======================================================
    // avaliarCandidatura
    // ======================================================
    public void avaliarCandidatura(Candidatura candidatura, Professor professor, StatusCandidatura novoStatus) {

        if (!controladorProjeto.verificarProfessorMembro(professor,
                candidatura.getPaeg().getAtividade().getProjeto())) {
            return;
        }

        candidatura.setStatus(novoStatus);
        catalogoCandidatura.atualizar(candidatura);

        // Notificar aluno
        String mensagem = "Sua candidatura no PAEG '" + candidatura.getPaeg().getNome() +
                "' foi " + novoStatus.toString().toLowerCase();

        catalogoNotificacao.addNotificacao(new Notificacao(candidatura.getAluno(), mensagem));
    }

    // ======================================================
    // registrarParticipacao (UC14)
    // ======================================================
    public void registrarParticipacao(Candidatura candidatura, Professor professor) {

        PAEG paeg = candidatura.getPaeg();

        if (!controladorProjeto.verificarProfessorMembro(professor, paeg.getAtividade().getProjeto())) {
            return;
        }

        candidatura.setStatus(StatusCandidatura.CONCLUIDO);
        candidatura.getAluno().adicionarHorasACC(paeg.getCargaHoraria());

        catalogoCandidatura.atualizar(candidatura);
    }

    // ======================================================
    // candidatar
    // ======================================================
    public void candidatar(PAEG paeg, Aluno aluno) {

        // Impede duplicadas
        catalogoCandidatura.verificarCandidaturaExistente(paeg, aluno);

        Candidatura nova = new Candidatura(aluno, paeg);
        catalogoCandidatura.addCandidatura(nova);

        // Notificação
        catalogoNotificacao.addNotificacao(
                new Notificacao(aluno, "Candidatura registrada para o PAEG: " + paeg.getNome())
        );
    }

    // ======================================================
    // visualizarMinhasCandidaturas
    // ======================================================
    public List<Candidatura> visualizarMinhasCandidaturas(Aluno aluno) {
        return catalogoCandidatura.buscarPorAluno(aluno);
    }

    // ======================================================
    // cancelarCandidatura (UC17)
    // ======================================================
    public void cancelarCandidatura(Candidatura candidatura) {

        if (!candidatura.isPendente()) {
            return; // Não pode cancelar algo já avaliado
        }

        catalogoCandidatura.excluir(candidatura);

        catalogoNotificacao.addNotificacao(
                new Notificacao(candidatura.getAluno(),
                        "Você cancelou sua candidatura no PAEG " + candidatura.getPaeg().getNome())
        );
    }
}
