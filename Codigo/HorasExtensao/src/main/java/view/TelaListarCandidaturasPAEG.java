package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorCandidatura;
import model.Candidatura;
import model.PAEG;
import model.Professor;
import model.StatusCandidatura;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarCandidaturasPAEG extends JFrame {

    public TelaListarCandidaturasPAEG(Professor prof, PAEG paeg) {

        setTitle("Candidaturas - " + paeg.getNome());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === BUSCAR PENDENTES ===
        List<Candidatura> candidaturas =
                Sistema.catalogoCandidatura.buscarPendentesPorPAEG(paeg);

        DefaultListModel<Candidatura> model = new DefaultListModel<>();
        for (Candidatura c : candidaturas) {
            model.addElement(c); // usa toString()
        }

        JList<Candidatura> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(lista), BorderLayout.CENTER);

        // === BOTÕES ===
        JButton btnAprovar = new JButton("Aprovar");
        JButton btnReprovar = new JButton("Reprovar");
        JButton btnVoltar = new JButton("Voltar");

        JPanel botoes = new JPanel();
        botoes.add(btnAprovar);
        botoes.add(btnReprovar);
        botoes.add(btnVoltar);

        add(botoes, BorderLayout.SOUTH);

        ControladorCandidatura cc = Sistema.controladorCandidatura;

        // ---- AÇÃO APROVAR ----
        btnAprovar.addActionListener(e -> {
            Candidatura selec = lista.getSelectedValue();
            if (selec == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma candidatura.");
                return;
            }

            boolean ok = cc.avaliarCandidatura(selec, prof, StatusCandidatura.APROVADO);
            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Não é possível aprovar. Limite de vagas atingido ou período não encerrado.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Candidato aprovado!");
            model.removeElement(selec); // some da lista
        });

        // ---- AÇÃO REPROVAR ----
        btnReprovar.addActionListener(e -> {
            Candidatura selec = lista.getSelectedValue();
            if (selec == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma candidatura.");
                return;
            }

            boolean ok = cc.avaliarCandidatura(selec, prof, StatusCandidatura.REPROVADO);
            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao reprovar. Período não encerrado ou professor não autorizado.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Candidato reprovado!");
            model.removeElement(selec);
        });

        // ---- VOLTAR ----
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPAEGDetalhes(prof, paeg.getAtividade(), paeg).setVisible(true);
        });
    }
}
