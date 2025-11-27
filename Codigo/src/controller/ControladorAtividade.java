package controller;

import model.Atividade;
import model.Projeto;
import java.util.Date;

/**
 * Controlador responsável pela gestão de Atividades.
 */
public class ControladorAtividade {
    
    /**
     * Adicionar Atividade
     * Cria e adiciona uma nova atividade ao projeto.
     */
    public String adicionarAtividade(Projeto projeto, String descricao, 
                                    int cargaHoraria, Date data) {
        
        if (projeto == null) {
            return "Erro: Projeto inválido.";
        }
        
        // Validar dados
        String validacao = validarDadosAtividade(descricao, cargaHoraria, data);
        if (!validacao.equals("OK")) {
            return validacao;
        }
        
        // Criar nova atividade
        Atividade novaAtividade = new Atividade(descricao, cargaHoraria, data);
        projeto.addAtividade(novaAtividade);
        
        return "Atividade adicionada com sucesso!";
    }
    
    /**
     * Excluir Atividade
     * Remove uma atividade do projeto.
     */
    public String excluirAtividade(Projeto projeto, Atividade atividade) {
        
        if (projeto == null) {
            return "Erro: Projeto inválido.";
        }
        
        if (atividade == null) {
            return "Erro: Atividade inválida.";
        }
        
        boolean removida = projeto.removeAtividade(atividade);
        
        if (removida) {
            return "Atividade excluída com sucesso!";
        } else {
            return "Erro: Não foi possível excluir a atividade.";
        }
    }
    
    /**
     * Valida os dados de uma atividade.
     */
    private String validarDadosAtividade(String descricao, int cargaHoraria, Date data) {
        
        if (descricao == null || descricao.trim().isEmpty()) {
            return "Erro: Descrição da atividade não pode estar vazia.";
        }
        
        if (cargaHoraria <= 0) {
            return "Erro: Carga horária deve ser maior que zero.";
        }
        
        if (data == null) {
            return "Erro: Data da atividade não pode estar vazia.";
        }
        
        return "OK";
    }
}
