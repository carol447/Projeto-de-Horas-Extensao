package catalog;

import java.util.ArrayList;
import java.util.List;
import model.Aluno;
import model.Notificacao;

public class CatalogoNotificacao {

    private List<Notificacao> notificacoes;

    public CatalogoNotificacao() {
        this.notificacoes = new ArrayList<>();
    }

    // ======================================================
    // addNotificacao
    // ======================================================
    public boolean addNotificacao(Notificacao notificacao) {
        if (notificacao == null) {
            return false;
        }
        notificacoes.add(notificacao);
        return true;
    }

    // ======================================================
    // buscarPorAluno
    // ======================================================
    public List<Notificacao> buscarPorAluno(Aluno aluno) {
        List<Notificacao> resultado = new ArrayList<>();

        for (Notificacao notif : notificacoes) {
            if (notif.getDestinatario().equals(aluno)) {
                resultado.add(notif);
            }
        }

        return resultado;
    }

    public List<Notificacao> getNotificacoes() {
        return new ArrayList<>(notificacoes); // proteção contra mutação externa
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
       this.notificacoes = notificacoes;
    }
}