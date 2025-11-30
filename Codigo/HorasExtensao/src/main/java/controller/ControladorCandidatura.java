package controller;

import java.util.List;

import model.Aluno;
import model.Candidatura;
import catalog.CatalogoAluno;
import catalog.CatalogoCandidatura;
import catalog.CatalogoNotificacao;
import java.util.Date;
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
    public boolean avaliarCandidatura(Candidatura candidatura, Professor professor, StatusCandidatura novoStatus) {

        PAEG paeg = candidatura.getPaeg();

        // 1. Só professor membro do projeto pode avaliar
        if (!controladorProjeto.verificarProfessorMembro(professor,
                paeg.getAtividade().getProjeto())) {
            return false;
        }

        // Se solicitar aprovação → verificar limite
        if (novoStatus == StatusCandidatura.APROVADO) {
            int aprovados = catalogoCandidatura.contarAprovadasPorPAEG(paeg);
            if (aprovados >= paeg.getMaximoCandidaturas()) {
                return false; // LIMITE ATINGIDO — professor NÃO PODE APROVAR MAIS
            }
        }

        // 2. Só pode avaliar se o período de candidaturas terminou
        // if (!paeg.isPeriodoInscricaoEncerrado()) {
        //    return false;
        //} só para testar
        // 4. Atualizar estado
        candidatura.setStatus(novoStatus);

        // CHAMAMOS ATUALIZAR() CONFORME O UML EXIGE
        catalogoCandidatura.atualizar(candidatura);

        // 5. Notificar aluno
        String msg = "Sua candidatura no PAEG '" + paeg.getNome()
                + "' foi " + novoStatus.toString().toLowerCase() + ".";

        catalogoNotificacao.addNotificacao(
                new Notificacao(candidatura.getAluno(), msg)
        );

        return true;
    }

    // ======================================================
    // registrarParticipacao (UC14)
    // ======================================================
    public boolean registrarParticipacao(Candidatura candidatura, Professor professor, boolean presente) {

        PAEG paeg = candidatura.getPaeg();

        // 1. Apenas professor membro do projeto pode registrar
        if (!controladorProjeto.verificarProfessorMembro(professor, paeg.getAtividade().getProjeto())) {
            return false;
        }

        //Date hoje = new Date();
        //if (!hoje.after(paeg.getDataFinalExecucao())) {
        //    return false;
        //}para testar

        if (!candidatura.isAprovada()) {
            return false;
        }

        // 4. Atualizar estado
        if (presente) {
            candidatura.setStatus(StatusCandidatura.CONCLUIDO);
            candidatura.getAluno().adicionarHorasACC(paeg.getCargaHoraria());
        } else {
            candidatura.setStatus(StatusCandidatura.REPROVADO); // AUSENTE
        }

        // 5. Registrar no catálogo
        catalogoCandidatura.atualizar(candidatura);

        // 6. Notificar aluno
        String mensagem = presente
                ? "Sua participação no PAEG '" + paeg.getNome() + "' foi concluída com sucesso."
                : "Você foi marcado como AUSENTE no PAEG '" + paeg.getNome() + "'.";

        catalogoNotificacao.addNotificacao(
                new Notificacao(candidatura.getAluno(), mensagem)
        );

        return true;
    }

    // ======================================================
    // candidatar
    // ======================================================
// ======================================================
// candidatar (UC16 — Candidatar-se a PAEG)
// ======================================================
    public boolean candidatar(PAEG paeg, Aluno aluno) {

        // 1. Verifica se o período está aberto
        if (!paeg.isPeriodoInscricaoAberto()) {
            return false;
        }

        // 2. Verifica duplicação
        try {
            catalogoCandidatura.verificarCandidaturaExistente(paeg, aluno);
        } catch (IllegalStateException e) {
            return false; // Já existe
        }

        Candidatura nova = paeg.criarCandidatura(aluno);

        if (nova == null) {
            // Já existe ou período fechado ou lotado
            return false;
        }

        // 2. Salvar no catálogo também
        catalogoCandidatura.addCandidatura(nova);

        // 3. Notificação
        catalogoNotificacao.addNotificacao(
                new Notificacao(aluno, "Candidatura registrada no PAEG: " + paeg.getNome())
        );

        return true;
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
