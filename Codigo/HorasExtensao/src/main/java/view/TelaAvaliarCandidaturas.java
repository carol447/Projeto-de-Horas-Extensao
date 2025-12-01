package view;

import com.mycompany.horasextensao.Sistema;
import model.Candidatura;
import model.PAEG;
import model.Professor;
import model.StatusCandidatura;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaAvaliarCandidaturas extends JFrame {

    public TelaAvaliarCandidaturas(Professor prof, PAEG paeg) {

        setTitle("Avaliar Candidaturas - " + paeg.getNome());
        setSize(500, 400);
        setLocationRelativeTo(null);

        List<Candidatura> pendentes
                = Sistema.catalogoCandidatura.buscarPendentesPorPAEG(paeg);

        DefaultListModel<Candidatura> model = new DefaultListModel<>();
        pendentes.forEach(model::addElement);

        JList<Candidatura> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnAprovar = new JButton("Aprovar");
        JButton btnReprovar = new JButton("Reprovar");
        JButton btnVoltar = new JButton("Voltar");

        JPanel botoes = new JPanel();
        botoes.add(btnAprovar);
        botoes.add(btnReprovar);
        botoes.add(btnVoltar);

        setLayout(new BorderLayout());
        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        // ===========================
        // APROVAR
        // ===========================
        btnAprovar.addActionListener(e -> {
            Candidatura c = lista.getSelectedValue();
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma candidatura.");
                return;
            }

            boolean ok = Sistema.controladorCandidatura.avaliarCandidatura(
                    c, prof, StatusCandidatura.APROVADO
            );

            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Vagas esgotadas ou período ainda aberto.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Candidatura aprovada!");
            model.removeElement(c);
        });

        // ===========================
        // REPROVAR
        // ===========================
        btnReprovar.addActionListener(e -> {
            Candidatura c = lista.getSelectedValue();
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma candidatura.");
                return;
            }

            Sistema.controladorCandidatura.avaliarCandidatura(
                    c, prof, StatusCandidatura.REPROVADO
            );

            JOptionPane.showMessageDialog(this, "Candidatura reprovada!");
            model.removeElement(c);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPAEGDetalhes(prof, paeg.getAtividade(), paeg).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaAvaliarCandidaturas.this);
    }
}
);
    }
    }

