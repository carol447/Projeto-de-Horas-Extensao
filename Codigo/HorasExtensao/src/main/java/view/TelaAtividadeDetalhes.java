package view;

import model.Atividade;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;

public class TelaAtividadeDetalhes extends JFrame {

    public TelaAtividadeDetalhes(Professor prof, Projeto projeto, Atividade atividade) {

        setTitle("Atividade: " + atividade.getNome());
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnPAEGs = new JButton("PAEGs");
        JButton btnAdicionarPAEG = new JButton("Adicionar PAEG");
        JButton btnVoltar = new JButton("Voltar");

        add(new JLabel("Atividade: " + atividade.getNome(), SwingConstants.CENTER));
        add(btnPAEGs);
        add(btnAdicionarPAEG);
        add(btnVoltar);

        btnPAEGs.addActionListener(e -> {
            dispose();
            new TelaListarPAEGsProfessor(prof, atividade).setVisible(true);
        });

        btnAdicionarPAEG.addActionListener(e -> {
            dispose();
            new TelaCadastrarPAEG(prof, atividade).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaListarAtividades(prof, projeto).setVisible(true);
        });
    }
}
