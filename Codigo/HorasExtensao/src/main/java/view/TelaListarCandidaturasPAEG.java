package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.PAEG;
import model.Candidatura;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarCandidaturasPAEG extends JFrame {

    public TelaListarCandidaturasPAEG(Professor prof, PAEG paeg) {

        setTitle("Candidaturas - " + paeg.getNome());
        setSize(500, 350);
        setLocationRelativeTo(null);

        List<Candidatura> candidaturas = paeg.getCandidaturas();

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Candidatura c : candidaturas) {
            model.addElement(c.getAluno().getNome() + " (" + c.getAluno().getRa() + ")");
        }

        JList<String> lista = new JList<>(model);

        JButton btnVoltar = new JButton("Voltar");

        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(btnVoltar, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPAEGDetalhes(prof, paeg.getAtividade(), paeg).setVisible(true);
        });
    }
}
