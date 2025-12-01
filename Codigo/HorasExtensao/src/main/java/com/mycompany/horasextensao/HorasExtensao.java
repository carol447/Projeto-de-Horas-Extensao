package com.mycompany.horasextensao;

import static com.mycompany.horasextensao.Sistema.catalogoProjeto;
import static com.mycompany.horasextensao.Sistema.repoProjeto;
import view.TelaLogin;

public class HorasExtensao {

    public static void main(String[] args) {
        Sistema.carregarDados();

        java.awt.EventQueue.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(Sistema::salvarDados));
    }
}
