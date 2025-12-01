package model;

import java.io.Serializable;

public class Notificacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private Aluno destinatario;
    private final String mensagem;

    public Notificacao(Aluno destinatario, String mensagem) {
        this.destinatario = destinatario;
        this.mensagem = mensagem;
    }

    public Aluno getDestinatario() {
        return destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }
    
   public void setDestinatario(Aluno destinatario) {  // ADICIONADO
        this.destinatario = destinatario;
    }
    
    
    
}
