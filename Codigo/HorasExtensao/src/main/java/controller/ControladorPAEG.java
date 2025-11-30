package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import catalog.CatalogoAtividade;
import catalog.CatalogoCandidatura;
import catalog.CatalogoPAEG;
import java.util.Calendar;
import model.PAEG;
import model.Atividade;
import model.Professor;
import model.Aluno;
import model.Projeto;

public class ControladorPAEG {

    private final CatalogoPAEG catalogoPAEG;
    private final CatalogoAtividade catalogoAtividade;
    private final CatalogoCandidatura catalogoCandidatura;
    private final ControladorProjeto controladorProjeto;

    public ControladorPAEG(CatalogoPAEG catalogoPAEG,
            CatalogoAtividade catalogoAtividade,
            CatalogoCandidatura catalogoCandidatura,
            ControladorProjeto controladorProjeto) {

        this.catalogoPAEG = catalogoPAEG;
        this.catalogoAtividade = catalogoAtividade;
        this.catalogoCandidatura = catalogoCandidatura;
        this.controladorProjeto = controladorProjeto;
    }

    // ======================================================
    // criarPAEG
    // ======================================================
    public boolean criarPAEG(Date dataInicialCandidatura, Date dataFinalCandidatura,
            Date dataInicialExecucao, Date dataFinalExecucao,
            String nome, int cargaHoraria, int maximoCandidaturas,
            Atividade atividade) {

        if (!validarDados(dataInicialCandidatura, dataFinalCandidatura,
                dataInicialExecucao, dataFinalExecucao,
                nome, cargaHoraria, maximoCandidaturas)) {
            return false;
        }

        PAEG novo = new PAEG(nome, cargaHoraria, maximoCandidaturas,
                dataInicialCandidatura, dataFinalCandidatura,
                dataInicialExecucao, dataFinalExecucao,
                atividade);

        if (!catalogoPAEG.addPAEG(novo)) {
            return false;
        }

        // vincula ao catálogo da atividade
        return catalogoAtividade.adicionarPAEG(atividade, novo);
    }

    // ======================================================
    // excluirPAEG
    // ======================================================
    public boolean excluirPAEG(PAEG paeg, Professor professor) {

        Atividade atividade = paeg.getAtividade();
        Projeto projeto = atividade.getProjeto();

        // Somente membro pode excluir
        if (!controladorProjeto.verificarProfessorMembro(professor, projeto)) {
            return false;
        }

        // Só pode excluir se não houver candidaturas
        if (!paeg.getCandidaturas().isEmpty()) {
            return false;
        }

        catalogoPAEG.excluir(paeg);
        atividade.getPaegs().remove(paeg);
        return true;
    }

    // ======================================================
    // listarPAEGsAbertosParaInscricao
    // ======================================================
    public List<PAEG> listarPAEGsAbertosParaInscricao() {
        return catalogoPAEG.buscarPAEGsAbertos();
    }

    // ======================================================
    // consultarPAEGsDisponiveis
    // ======================================================
    public List<PAEG> consultarPAEGsDisponiveis(Aluno aluno) {

        List<PAEG> resultado = new ArrayList<>();

        Date agora = new Date();

        for (PAEG paeg : catalogoPAEG.getPaegs()) {

            boolean periodoAberto = !agora.before(paeg.getDataInicialCandidatura())
                    && !agora.after(paeg.getDataFinalCandidatura());

            boolean doMesmoCurso = paeg.getAtividade()
                    .getProjeto()
                    .getCurso() == aluno.getCurso();

            if (periodoAberto && doMesmoCurso) {
                resultado.add(paeg);
            }
        }

        return resultado;
    }

    // ======================================================
    // validarDados (private conforme o UML)
    // ======================================================
    private boolean validarDados(Date dataInicialCandidatura, Date dataFinalCandidatura,
            Date dataInicialExecucao, Date dataFinalExecucao,
            String nome, int cargaHoraria, int maximoCandidaturas) {

        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]{3,}$")) {
            return false;
        }

        if (cargaHoraria <= 0) {
            return false;
        }

        if (maximoCandidaturas <= 0) {
            return false;
        }

        if (dataInicialCandidatura == null || dataFinalCandidatura == null
                || dataInicialExecucao == null || dataFinalExecucao == null) {
            return false;
        }

        // Regras do trabalho (UC9):
        if (dataInicialCandidatura.after(dataFinalCandidatura)) {
            return false;
        }

        if (dataInicialExecucao.after(dataFinalExecucao)) {
            return false;
        }

        // Execução só pode começar no mesmo dia ou depois do fim das inscrições
        if (dataInicialExecucao.before(dataFinalCandidatura)) {
            return false;
        }

        // Nenhuma data pode ser anterior ao dia atual
        Date hoje = inicioDoDia(new Date());

// Início da candidatura pode ser hoje, mas não antes
        if (dataInicialCandidatura.before(hoje)) {
            return false;
        }

// Demais datas não podem ser hoje nem antes (devem ser futuras)
        if (!dataFinalCandidatura.after(hoje)) {
            return false;
        }

        if (!dataInicialExecucao.after(hoje)) {
            return false;
        }

        if (!dataFinalExecucao.after(hoje)) {
            return false;
        }

        return true;
    }

    private Date inicioDoDia(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
