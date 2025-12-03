package com.mycompany.horasextensao;

import controller.*;
import repository.*;
import catalog.*;
import java.util.ArrayList;
import java.util.List;
import model.Aluno;
import model.Atividade;
import model.Candidatura;
import model.Notificacao;
import model.PAEG;
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

        ajustarProfessoresDosProjetos();

        for (Notificacao n : catalogoNotificacao.getNotificacoes()) {
            String cpf = n.getDestinatario().getCpf();

            // aluno real do catálogo
            Aluno real = Sistema.catalogoAluno.encontrarAlunoPorCpf(cpf);

            if (real != null) {
                n.setDestinatario(real); // substitui aluno duplicado pelo aluno correto
            }
        }

        for (Candidatura c : catalogoCandidatura.getCandidaturas()) {
            for (Aluno real : catalogoAluno.getAlunos()) {
                if (real.getCpf().equals(c.getAluno().getCpf())) {
                    // substitui o aluno fantasma pelo aluno REAL
                    c.setAluno(real);
                    break;
                }
            }
        }

        for (Candidatura c : catalogoCandidatura.getCandidaturas()) {
            PAEG salvo = c.getPaeg();

            for (PAEG real : catalogoPAEG.getPaegs()) {

                boolean mesmo
                        = real.getNome().equals(salvo.getNome())
                        && real.getAtividade().getNome().equals(salvo.getAtividade().getNome())
                        && real.getAtividade().getProjeto().getNome().equals(salvo.getAtividade().getProjeto().getNome())
                        && real.getAtividade().getProjeto().getCriador().getCpf()
                                .equals(salvo.getAtividade().getProjeto().getCriador().getCpf());

                if (mesmo) {
                    // substitui PAeg fantasma pelo REAL
                    c.setPaeg(real);

                    // garante que o PAEG REAL receba a candidatura
                    if (!real.getCandidaturas().contains(c)) {
                        real.getCandidaturas().add(c);
                    }

                    break;
                }
            }
        }

        if (catalogoPAEG.getPaegs().isEmpty()
                && catalogoProjeto.getProjetos().isEmpty()
                && catalogoAluno.getAlunos().isEmpty()) {

            TesteIntegracao.CenariosPAEG.gerar();
            salvarDados();
        }

        ajustarPAEGsDasAtividades();

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

    private static void ajustarPAEGsDasAtividades() {

    for (Atividade real : catalogoAtividade.getAtividades()) {
        real.getPaegs().clear(); // ZERA antes de reconstruir
    }

    for (PAEG paeg : catalogoPAEG.getPaegs()) {
        Atividade atividadeFantasma = paeg.getAtividade();

        for (Atividade real : catalogoAtividade.getAtividades()) {

            boolean mesmaAtividade
                    = real.getNome().equalsIgnoreCase(atividadeFantasma.getNome())
                    && real.getProjeto().getNome().equalsIgnoreCase(
                            atividadeFantasma.getProjeto().getNome()
                    )
                    && real.getProjeto().getCriador().getCpf().equals(
                            atividadeFantasma.getProjeto().getCriador().getCpf()
                    );

            if (mesmaAtividade) {
                paeg.setAtividade(real); // substitui fantasma pelo real

                if (!real.getPaegs().contains(paeg)) {
                    real.getPaegs().add(paeg);
                }
            }
        }
    }
}


}
