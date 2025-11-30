package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Atividade implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String descricao;
    private List<PAEG> paegs;
    private Projeto projeto;

    public Atividade(String nome, String descricao, Projeto projeto) {
        this.nome = nome;
        this.descricao = descricao;
        this.projeto = projeto;
        this.paegs = new ArrayList<>();
    }

    // GETTERS
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

    public Projeto getProjeto() {
        return projeto;
    }

    public List<PAEG> getPaegs() {
        return paegs;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPaegs(List<PAEG> paegs) {
        this.paegs = paegs;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
    

    public boolean adicionarPAEG(PAEG paeg) {
        for (PAEG p : paegs) {
            if (p.getNome().equalsIgnoreCase(paeg.getNome())) {
                return false;
            }
        }

        paegs.add(paeg);
        return true;
    }

    public boolean possuiPAEGs() {
        return !paegs.isEmpty();
    }
}
