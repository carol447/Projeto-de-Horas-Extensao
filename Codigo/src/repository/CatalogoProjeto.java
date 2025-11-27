package repository;

import model.Projeto;
import model.Professor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Catálogo que armazena e gerencia projetos de extensão.
 */
public class CatalogoProjeto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Projeto> projetos;
    
    public CatalogoProjeto() {
        this.projetos = new ArrayList<>();
    }
    
    /**
     * Adiciona um projeto ao catálogo.
     */
    public void adicionar(Projeto projeto) {
        projetos.add(projeto);
    }
    
    /**
     * Remove um projeto do catálogo.
     */
    public boolean remover(Projeto projeto) {
        return projetos.remove(projeto);
    }
    
    /**
     * Lista todos os projetos de um coordenador específico.
     */
    public List<Projeto> listarPorCoordenador(Professor coordenador) {
        return projetos.stream()
                .filter(p -> p.getCoordenador().equals(coordenador))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca um projeto por título (auxiliar para validações).
     */
    public Projeto buscarPorTitulo(String titulo) {
        for (Projeto projeto : projetos) {
            if (projeto.getTitulo().equalsIgnoreCase(titulo)) {
                return projeto;
            }
        }
        return null;
    }
    
    /**
     * Lista projetos onde o professor é membro da equipe.
     */
    public List<Projeto> listarProjetosDoProf(Professor professor) {
        return projetos.stream()
                .filter(p -> p.isMembro(professor))
                .collect(Collectors.toList());
    }
    
    /**
     * Retorna todos os projetos.
     */
    public List<Projeto> getTodosProjetos() {
        return new ArrayList<>(projetos);
    }
    
    /**
     * Retorna a quantidade de projetos cadastrados.
     */
    public int getTotalProjetos() {
        return projetos.size();
    }
}
