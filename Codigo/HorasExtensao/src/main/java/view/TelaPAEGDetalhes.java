package view;

import model.Atividade;
import model.PAEG;
import model.Professor;

import javax.swing.*;
import java.awt.*;

public class TelaPAEGDetalhes extends JFrame {

    public TelaPAEGDetalhes(Professor prof, Atividade atividade, PAEG paeg) {

        setTitle("PAEG: " + paeg.getNome());
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnCandidaturas = new JButton("Candidaturas");
        JButton btnVoltar = new JButton("Voltar");

        add(new JLabel("PAEG: " + paeg.getNome(), SwingConstants.CENTER));
        add(btnCandidaturas);
        add(btnVoltar);

        btnCandidaturas.addActionListener(e -> {
            dispose();
            new TelaListarCandidaturasPAEG(prof, paeg).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaListarPAEGsProfessor(prof, atividade).setVisible(true);
        });
    }
}
