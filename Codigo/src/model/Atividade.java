package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe que representa uma Atividade de Extensão.
 */
public class Atividade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String descricao;
    private int cargaHoraria;
    private Date data;
    
    /**
     * Construtor completo.
     */
    public Atividade(String descricao, int cargaHoraria, Date data) {
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.data = data;
    }
    
    // Getters
    public String getDescricao() {
        return descricao;
    }
    
    public int getCargaHoraria() {
        return cargaHoraria;
    }
    
    public Date getData() {
        return data;
    }
    
    // Setters
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
    public void setData(Date data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "Atividade: " + descricao + 
               " | Carga Horária: " + cargaHoraria + "h" +
               " | Data: " + data;
    }
}
