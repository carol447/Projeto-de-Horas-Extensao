package com.mycompany.horasextensao;

import controller.*;
import repository.*;
import catalog.*;
import java.util.ArrayList;
import java.util.List;
import model.Professor;
import model.Projeto;

public class Sistema {

    // =======================
    // Repositórios
    // =======================
    public static RepositorioAluno repoAluno = new RepositorioAluno();
    public static RepositorioProfessor repoProfessor = new RepositorioProfessor();
    public static RepositorioProjeto repoProjeto = new RepositorioProjeto();
    public static RepositorioAtividade repoAtividade = new RepositorioAtividade();
    public static RepositorioPAEG repoPAEG = new RepositorioPAEG();
    public static RepositorioCandidatura repoCandidatura = new RepositorioCandidatura();
    public static RepositorioNotificacao repoNotificacao = new RepositorioNotificacao();

    // =======================
    // Catálogos
    // =======================
    public static CatalogoAluno catalogoAluno = new CatalogoAluno();
    public static CatalogoProfessor catalogoProfessor = new CatalogoProfessor();
    public static CatalogoProjeto catalogoProjeto = new CatalogoProjeto();
    public static CatalogoAtividade catalogoAtividade = new CatalogoAtividade();
    public static CatalogoPAEG catalogoPAEG = new CatalogoPAEG();
    public static CatalogoCandidatura catalogoCandidatura = new CatalogoCandidatura();
    public static CatalogoNotificacao catalogoNotificacao = new CatalogoNotificacao();

    // =======================
    // Controladores
    // =======================
    public static ControladorAluno controladorAluno
            = new ControladorAluno(catalogoAluno);

    public static ControladorProfessor controladorProfessor
            = new ControladorProfessor(catalogoProfessor);

    public static ControladorProjeto controladorProjeto
            = new ControladorProjeto(catalogoProjeto);

    public static ControladorAtividade controladorAtividade
            = new ControladorAtividade(catalogoAtividade, catalogoProjeto, controladorProjeto);

    public static ControladorPAEG controladorPAEG
            = new ControladorPAEG(catalogoPAEG, catalogoAtividade, catalogoCandidatura, controladorProjeto);

    public static ControladorCandidatura controladorCandidatura
            = new ControladorCandidatura(catalogoCandidatura, controladorProjeto, catalogoAluno, catalogoNotificacao);

    public static ControladorNotificacao controladorNotificacao
            = new ControladorNotificacao(catalogoNotificacao);

    // =======================
    // Métodos de inicialização
    // =======================
    public static void carregarDados() {
        catalogoAluno.setAlunos(repoAluno.carregar());
        catalogoProfessor.setProfessores(repoProfessor.carregar());
        catalogoProjeto.setProjetos(repoProjeto.carregar());
        catalogoAtividade.setAtividades(repoAtividade.carregar());
        catalogoPAEG.setPaegs(repoPAEG.carregar());
        catalogoCandidatura.setCandidaturas(repoCandidatura.carregar());
        catalogoNotificacao.setNotificacoes(repoNotificacao.carregar());

        System.out.println("==== DEBUG CARREGAR DADOS ====");

        System.out.println("Professores carregados: " + catalogoProfessor.getProfessores().size());
        for (Professor pr : catalogoProfessor.getProfessores()) {
            System.out.println(" - Prof catálogo: " + pr.getNome() + " CPF=" + pr.getCpf()
                    + " hash=" + System.identityHashCode(pr));
        }

        System.out.println("Projetos carregados: " + catalogoProjeto.getProjetos().size());
        for (Projeto p : catalogoProjeto.getProjetos()) {
            System.out.println("Projeto: " + p.getNome());
            for (Professor pr : p.getProfessores()) {
                System.out.println(" - Prof do projeto: " + pr.getNome() + " CPF=" + pr.getCpf()
                        + " hash=" + System.identityHashCode(pr));
            }
        }
        ajustarProfessoresDosProjetos();
    }

    public static void salvarDados() {
        repoAluno.salvar(catalogoAluno.getAlunos());
        repoProfessor.salvar(catalogoProfessor.getProfessores());
        repoProjeto.salvar(catalogoProjeto.getProjetos());
        repoAtividade.salvar(catalogoAtividade.getAtividades());
        repoPAEG.salvar(catalogoPAEG.getPaegs());
        repoCandidatura.salvar(catalogoCandidatura.getCandidaturas());
        repoNotificacao.salvar(catalogoNotificacao.getNotificacoes());
    }
    
    private static void ajustarProfessoresDosProjetos() {

    List<Professor> todos = catalogoProfessor.getProfessores();

    for (Projeto p : catalogoProjeto.getProjetos()) {

        List<Professor> novaLista = new ArrayList<>();

        for (Professor antigo : p.getProfessores()) {
            for (Professor real : todos) {

                if (real.getCpf().equals(antigo.getCpf())) {
                    novaLista.add(real);
                }
            }
        }

        p.setProfessores(novaLista);
    }
}

}
