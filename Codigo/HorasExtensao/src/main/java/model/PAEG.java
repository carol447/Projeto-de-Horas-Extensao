package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PAEG implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date dataInicialCandidatura;
    private Date dataFinalCandidatura;
    private Date dataInicialExecucao;
    private Date dataFinalExecucao;

    private String nome;
    private int cargaHoraria;
    private int maximoCandidaturas;

    private Atividade atividade;

    private final List<Candidatura> candidaturas;

    public PAEG(String nome, int cargaHoraria, int maximoCandidaturas,
            Date dataInicialCandidatura, Date dataFinalCandidatura,
            Date dataInicialExecucao, Date dataFinalExecucao,
            Atividade atividade) {

        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.maximoCandidaturas = maximoCandidaturas;

        this.dataInicialCandidatura = dataInicialCandidatura;
        this.dataFinalCandidatura = dataFinalCandidatura;
        this.dataInicialExecucao = dataInicialExecucao;
        this.dataFinalExecucao = dataFinalExecucao;

        this.atividade = atividade;
        this.candidaturas = new ArrayList<>();
    }

    public Date getDataInicialCandidatura() {
        return dataInicialCandidatura;
    }

    public Date getDataFinalCandidatura() {
        return dataFinalCandidatura;
    }

    public Date getDataInicialExecucao() {
        return dataInicialExecucao;
    }

    public Date getDataFinalExecucao() {
        return dataFinalExecucao;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public int getMaximoCandidaturas() {
        return maximoCandidaturas;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public List<Candidatura> getCandidaturas() {
        return candidaturas;
    }

    public void setDataInicialCandidatura(Date dataInicialCandidatura) {
        this.dataInicialCandidatura = dataInicialCandidatura;
    }

    public void setDataFinalCandidatura(Date dataFinalCandidatura) {
        this.dataFinalCandidatura = dataFinalCandidatura;
    }

    public void setDataInicialExecucao(Date dataInicialExecucao) {
        this.dataInicialExecucao = dataInicialExecucao;
    }

    public void setDataFinalExecucao(Date dataFinalExecucao) {
        this.dataFinalExecucao = dataFinalExecucao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setMaximoCandidaturas(int maximoCandidaturas) {
        this.maximoCandidaturas = maximoCandidaturas;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public boolean isPeriodoInscricaoEncerrado() {
        Date agora = new Date();
        return agora.after(dataFinalCandidatura);
    }

    public boolean isPeriodoInscricaoAberto() {
        Date agora = new Date();
        return !agora.before(dataInicialCandidatura)
                && !agora.after(dataFinalCandidatura);
    }

    public Candidatura criarCandidatura(Aluno aluno) {

        if (!isPeriodoInscricaoAberto()) {
            return null;
        }

        // Duplication check
        for (Candidatura c : candidaturas) {
            if (c.getAluno().equals(aluno)) {
                return null;
            }
        }


        Candidatura nova = new Candidatura(aluno, this);
        candidaturas.add(nova);

        return nova;
    }
}
