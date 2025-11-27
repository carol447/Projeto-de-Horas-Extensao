package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe que representa um Projeto de Extensão.
 * 
 */
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String titulo;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    private String status; // "EM_ANDAMENTO" ou "CONCLUIDO"
    private Professor coordenador;
    
    // Relacionamentos
    private List<Professor> equipe;
    private List<Atividade> atividades;
    
    /**
     * Construtor completo.
     */
    public Projeto(String titulo, String descricao, Date dataInicio, Date dataFim, Professor coordenador) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.coordenador = coordenador;
        this.status = "EM_ANDAMENTO";
        this.equipe = new ArrayList<>();
        this.atividades = new ArrayList<>();
        
        // O coordenador automaticamente faz parte da equipe
        this.equipe.add(coordenador);
    }
    
    // Getters
    public String getTitulo() {
        return titulo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public Date getDataInicio() {
        return dataInicio;
    }
    
    public Date getDataFim() {
        return dataFim;
    }
    
    public String getStatus() {
        return status;
    }
    
    public Professor getCoordenador() {
        return coordenador;
    }
    
    public List<Professor> getEquipe() {
        return new ArrayList<>(equipe);
    }
    
    public List<Atividade> getAtividades() {
        return new ArrayList<>(atividades);
    }
    
    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Verifica se um professor já é membro da equipe.
     */
    public boolean isMembro(Professor professor) {
        return equipe.contains(professor);
    }
    
    /**
     * Adiciona um professor à equipe do projeto.
     */
    public void addProfessor(Professor professor) {
        if (!isMembro(professor)) {
            equipe.add(professor);
        }
    }
    
    /**
     * Remove um professor da equipe (exceto o coordenador).
     */
    public boolean removeProfessor(Professor professor) {
        if (professor.equals(coordenador)) {
            return false; // Não pode remover o coordenador
        }
        return equipe.remove(professor);
    }
    
    /**
     * Adiciona uma atividade ao projeto.
     */
    public void addAtividade(Atividade atividade) {
        atividades.add(atividade);
    }
    
    /**
     * Remove uma atividade do projeto.
     */
    public boolean removeAtividade(Atividade atividade) {
        return atividades.remove(atividade);
    }
    
    /**
     * Retorna a quantidade de professores na equipe.
     */
    public int getTamanhoEquipe() {
        return equipe.size();
    }
    
    /**
     * Retorna a quantidade de atividades do projeto.
     */
    public int getTotalAtividades() {
        return atividades.size();
    }
    
    @Override
    public String toString() {
        return "Projeto: " + titulo + 
               " | Status: " + status +
               " | Coordenador: " + coordenador.getNome() +
               " | Equipe: " + equipe.size() + " professor(es)" +
               " | Atividades: " + atividades.size();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Projeto projeto = (Projeto) obj;
        return titulo.equals(projeto.titulo) && 
               coordenador.equals(projeto.coordenador);
    }
    
    @Override
    public int hashCode() {
        return titulo.hashCode() + coordenador.hashCode();
    }
}