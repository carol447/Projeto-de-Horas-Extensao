package controller;

import model.Atividade;
import catalog.CatalogoAtividade;
import catalog.CatalogoProjeto;
import model.Projeto;
import model.Professor;

public class ControladorAtividade {

    private final CatalogoAtividade catalogoAtividade;
    private final CatalogoProjeto catalogoProjeto;
    private final ControladorProjeto controladorProjeto;

    public ControladorAtividade(CatalogoAtividade catalogoAtividade,
            CatalogoProjeto catalogoProjeto,
            ControladorProjeto controladorProjeto) {
        this.catalogoAtividade = catalogoAtividade;
        this.catalogoProjeto = catalogoProjeto;
        this.controladorProjeto = controladorProjeto;
    }

    public boolean criarAtividade(String nome, String descricao, Projeto projeto) {

        if (!validarDados(nome, descricao)) {
            return false;
        }

        Atividade nova = new Atividade(nome, descricao, projeto);

        if (!catalogoAtividade.addAtividade(nova)) {
            return false; // duplicada
        }

        // Vincula a atividade ao projeto (UML: CatalogoProjeto.adicionarAtividade)
        catalogoProjeto.adicionarAtividade(projeto, nova);

        return true;
    }

    public boolean excluirAtividade(Atividade atividade, Professor professor) {
        Projeto projeto = atividade.getProjeto();

        // 1. Somente professor membro pode excluir
        if (!controladorProjeto.verificarProfessorMembro(professor, projeto)) {
            return false;
        }

        // 2. Verificar se existe PAEG vinculado
        if (!atividade.getPaegs().isEmpty()) {
            return false; // Fluxo alternativo: possui PAEGs → não pode excluir
        }

        // 3. Remover do catálogo de atividades
        catalogoAtividade.excluir(atividade);

        // 4. Remover a atividade do projeto
        projeto.getAtividades().remove(atividade);

        return true;
    }

    private boolean validarDados(String nome, String descricao) {

        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]{3,}$")) {
            return false;
        }

        if (descricao == null || descricao.trim().length() < 3) {
            return false;
        }

        return true;
    }
}
