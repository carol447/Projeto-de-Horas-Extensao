package controller;

import java.util.List;

import model.Aluno;
import catalog.CatalogoNotificacao;
import model.Notificacao;

public class ControladorNotificacao {

    private final CatalogoNotificacao catalogoNotificacao;

    public ControladorNotificacao(CatalogoNotificacao catalogoNotificacao) {
        this.catalogoNotificacao = catalogoNotificacao;
    }

    // ======================================================
    // visualizarMinhasNotificacoes
    // ======================================================
    public List<Notificacao> visualizarMinhasNotificacoes(Aluno aluno) {
        return catalogoNotificacao.buscarPorAluno(aluno);
    }

    // ======================================================
    // criarNotificacao
    // ======================================================
    public void criarNotificacao(Aluno destinatario, String mensagem) {

        if (destinatario == null || mensagem == null || mensagem.trim().isEmpty()) {
            return;
        }

        Notificacao nova = new Notificacao(destinatario, mensagem);
        catalogoNotificacao.addNotificacao(nova);
    }
}

