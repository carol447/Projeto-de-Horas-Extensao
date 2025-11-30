package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorAtividade;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;

public class TelaCadastrarAtividade extends JFrame {

    public TelaCadastrarAtividade(Professor professor, Projeto projeto) {
        setTitle("Cadastrar Atividade");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JTextField txtTitulo = new JTextField();
        JTextField txtDescricao = new JTextField();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Descrição:"));
        add(txtDescricao);
        add(btnSalvar);
        add(btnVoltar);

        btnSalvar.addActionListener(e -> {
            ControladorAtividade ca = Sistema.controladorAtividade;

            boolean ok = ca.criarAtividade(
                    txtTitulo.getText(),
                    txtDescricao.getText(),
                    projeto
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Atividade criada!");
                Sistema.salvarDados();
                dispose();
                new TelaProjetoDetalhes(professor, projeto).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar atividade.");
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaProjetoDetalhes(professor, projeto).setVisible(true);
        });
    }
}
