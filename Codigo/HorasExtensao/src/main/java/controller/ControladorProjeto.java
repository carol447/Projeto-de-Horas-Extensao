package controller;

import java.util.List;
import catalog.CatalogoProfessor;
import catalog.CatalogoProjeto;
import model.Cursos;
import model.Projeto;
import model.Professor;

public class ControladorProjeto {

    private final CatalogoProjeto catalogoProjeto;

    public ControladorProjeto(CatalogoProjeto catalogoProjeto) {
        this.catalogoProjeto = catalogoProjeto;
    }

    public boolean criarProjeto(String nome, String descricao, String curso,
            Professor criador) {

        if (!validarDados(nome, descricao)) {
            return false;
        }

        Cursos cursoEnum;
        try {
            cursoEnum = Cursos.valueOf(curso.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false; // curso inválido
        }

        Projeto novo = new Projeto(nome, descricao, cursoEnum, criador);
        return catalogoProjeto.addProjeto(novo);
    }

    public List<Professor> exibirProfessores(CatalogoProfessor catalogoProfessor) {
        return catalogoProfessor.getProfessores();
    }

    public boolean atribuirProfessor(Projeto projeto, Professor profAlvo, Professor profLogado) {

        if (projeto == null || profAlvo == null || profLogado == null) {
            return false;
        }

        // Apenas membros podem adicionar outros professores
        if (!projeto.isMembro(profLogado)) {
            return false;
        }

        projeto.adicionarProfessor(profAlvo);
        return true;
    }

    public boolean verificarProfessorMembro(Professor professor, Projeto projeto) {
        return projeto != null && projeto.isMembro(professor);
    }

    public List<Projeto> consultarMeusProjetos(Professor professor) {
        return catalogoProjeto.buscarPorMembro(professor);
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
