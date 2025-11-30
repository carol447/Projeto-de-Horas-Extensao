package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String descricao;
    private Cursos curso;
    private List<Professor> professores;
    private List<Atividade> atividades;

    public Projeto(String nome, String descricao, Cursos curso, Professor criador) {
        this.nome = nome;
        this.descricao = descricao;
        this.curso = curso;
        this.professores = new ArrayList<>();
        this.atividades = new ArrayList<>();
        this.professores.add(criador);
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cursos getCurso() {
        return curso;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public boolean adicionarAtividade(Atividade atividade) {
        for (Atividade a : atividades) {
            if (a.getNome().equalsIgnoreCase(atividade.getNome())) {
                return false;
            }
        }
        atividades.add(atividade);
        return true;
    }

    public boolean isMembro(Professor professor) {
        if (professor == null) {
            return false;
        }

        for (Professor p : professores) {
            if (p != null && p.getCpf() != null
                    && p.getCpf().equals(professor.getCpf())) {
                return true;
            }
        }

        return false;
    }

    public void adicionarProfessor(Professor prof) {
        if (prof == null || prof.getCpf() == null) {
            return;
        }

        // evita duplicar pelo CPF
        if (!isMembro(prof)) {
            professores.add(prof);
        }
    }

    public Professor getCriador() {
        return professores.isEmpty() ? null : professores.get(0);
    }
}
