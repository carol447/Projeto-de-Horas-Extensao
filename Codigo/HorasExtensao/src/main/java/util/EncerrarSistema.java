package util;

import com.mycompany.horasextensao.Sistema;
import javax.swing.*;

public class EncerrarSistema {

    public static void encerrarAplicacao(JFrame janelaAtual) {

        int op = JOptionPane.showConfirmDialog(
                janelaAtual,
                "Deseja realmente encerrar o sistema?\nTodos os dados serão salvos.",
                "Encerrar Sistema",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (op == JOptionPane.YES_OPTION) {

            try {
                Sistema.salvarDados();   // SALVA TUDO
                System.exit(0);          // FINALIZA COMPLETAMENTE
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        janelaAtual,
                        "Erro ao salvar os dados. Tente novamente.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
