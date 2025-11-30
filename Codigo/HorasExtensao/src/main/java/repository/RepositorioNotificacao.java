package repository;

import model.Notificacao;

public class RepositorioNotificacao extends RepositorioBase<Notificacao> {

    public RepositorioNotificacao() {
        super("dados/notificacoes.txt");
    }
}
