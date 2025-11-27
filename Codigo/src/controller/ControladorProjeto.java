package controller;

import model.Projeto;
import model.Professor;
import repository.CatalogoProjeto;
import repository.CatalogoProfessor;
import java.util.Date;
import java.util.List;

/**
 * Controlador responsável pela gestão de Projetos de Extensão.
 * 
 */
public class ControladorProjeto {
    private CatalogoProjeto catalogoProjeto;
    private CatalogoProfessor catalogoProfessor;
    
    public ControladorProjeto(CatalogoProjeto catalogoProjeto, CatalogoProfessor catalogoProfessor) {
        this.catalogoProjeto = catalogoProjeto;
        this.catalogoProfessor = catalogoProfessor;
    }
    
    /**
     * Criar Projeto
     * Cria um novo projeto de extensão.
     */
    public String criarProjeto(String titulo, String descricao, Date dataInicio, 
                              Date dataFim, Professor professorLogado) {
        
        // Validar dados
        String validacao = validarDadosProjeto(titulo, descricao, dataInicio, dataFim);
        if (!validacao.equals("OK")) {
            return validacao;
        }
        
        // Verificar se já existe projeto com mesmo título do mesmo coordenador
        List<Projeto> projetosCoordenador = catalogoProjeto.listarPorCoordenador(professorLogado);
        for (Projeto p : projetosCoordenador) {
            if (p.getTitulo().equalsIgnoreCase(titulo)) {
                return "Erro: Você já possui um projeto com este título.";
            }
        }
        
        // Criar novo projeto
        Projeto novoProjeto = new Projeto(titulo, descricao, dataInicio, dataFim, professorLogado);
        catalogoProjeto.adicionar(novoProjeto);
        
        return "Projeto criado com sucesso!";
    }
    
    /**
     * Consultar Meus Projetos
     * Lista todos os projetos do professor logado como coordenador.
     */
    public List<Projeto> consultarMeusProjetos(Professor professorLogado) {
        return catalogoProjeto.listarPorCoordenador(professorLogado);
    }
    
    /**
     * Atribuir Professor à Equipe
     * Adiciona um professor à equipe do projeto.
     */
    public String atribuirProfessor(Projeto projeto, Professor professorSelecionado) {
        
        if (professorSelecionado == null) {
            return "Erro: Professor inválido.";
        }
        
        // Verificar se já é membro
        if (projeto.isMembro(professorSelecionado)) {
            return "Erro: Este professor já faz parte da equipe do projeto.";
        }
        
        // Adicionar à equipe
        projeto.addProfessor(professorSelecionado);
        
        return "Professor adicionado à equipe com sucesso!";
    }
    
    /**
     * Excluir Projeto
     * Remove um projeto do catálogo.
     */
    public String excluirProjeto(Projeto projeto) {
        
        if (projeto == null) {
            return "Erro: Projeto inválido.";
        }
        
        boolean removido = catalogoProjeto.remover(projeto);
        
        if (removido) {
            return "Projeto excluído com sucesso!";
        } else {
            return "Erro: Não foi possível excluir o projeto.";
        }
    }
    
    /**
     * Valida os dados de um projeto.
     */
    private String validarDadosProjeto(String titulo, String descricao, 
                                      Date dataInicio, Date dataFim) {
        
        if (titulo == null || titulo.trim().isEmpty()) {
            return "Erro: Título não pode estar vazio.";
        }
        
        if (descricao == null || descricao.trim().isEmpty()) {
            return "Erro: Descrição não pode estar vazia.";
        }
        
        if (dataInicio == null) {
            return "Erro: Data de início não pode estar vazia.";
        }
        
        if (dataFim == null) {
            return "Erro: Data de fim não pode estar vazia.";
        }
        
        if (dataFim.before(dataInicio)) {
            return "Erro: Data de fim deve ser posterior à data de início.";
        }
        
        return "OK";
    }
    
    /**
     * Retorna o catálogo de professores (para busca na interface).
     */
    public CatalogoProfessor getCatalogoProfessor() {
        return catalogoProfessor;
    }
}