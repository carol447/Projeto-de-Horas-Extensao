package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;

import javax.swing.*;
import java.awt.*;

public class TelaHomeAluno extends JFrame {

    public TelaHomeAluno(Aluno aluno) {

        setTitle("Área do Aluno");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel lbl = new JLabel("Bem-vindo, " + aluno.getNome(), SwingConstants.CENTER);

        JButton btnPAEGs = new JButton("PAEGs Disponíveis");
        JButton btnMinhasCandidaturas = new JButton("Minhas Candidaturas");
        JButton btnSair = new JButton("Sair");

        add(lbl);
        add(btnPAEGs);
        add(btnMinhasCandidaturas);
        add(btnSair);

        btnPAEGs.addActionListener(e -> {
            dispose();
            new TelaListarPAEGsDisponiveis(aluno).setVisible(true);
        });

        btnMinhasCandidaturas.addActionListener(e -> {
            dispose();
            new TelaMinhasCandidaturas(aluno).setVisible(true);
        });

        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
    }
}
