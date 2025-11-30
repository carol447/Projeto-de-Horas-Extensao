package view;

import com.mycompany.horasextensao.Sistema;
import model.Candidatura;
import model.PAEG;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRegistrarParticipacao extends JFrame {

    public TelaRegistrarParticipacao(Professor prof, PAEG paeg) {

        setTitle("Registrar Participação - " + paeg.getNome());
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Apenas APROVADOS (UC14 pré-condição)
        List<Candidatura> aprovados = Sistema.catalogoCandidatura.buscarAprovadosPorPAEG(paeg)
                .stream()
                .filter(Candidatura::isAprovada)
                .toList();

        DefaultListModel<Candidatura> model = new DefaultListModel<>();
        aprovados.forEach(model::addElement);

        JList<Candidatura> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnPresente = new JButton("Marcar Presente");
        JButton btnAusente = new JButton("Marcar Ausente");
        JButton btnVoltar = new JButton("Voltar");

        JPanel comandos = new JPanel();
        comandos.add(btnPresente);
        comandos.add(btnAusente);
        comandos.add(btnVoltar);

        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(comandos, BorderLayout.SOUTH);

        // Registrar Presença
        btnPresente.addActionListener(e -> {
            Candidatura c = lista.getSelectedValue();
            if (c == null) return;

            boolean ok = Sistema.controladorCandidatura.registrarParticipacao(c, prof, true);

            if (ok) {
                Sistema.salvarDados();
                JOptionPane.showMessageDialog(this, "Participação registrada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível registrar participação.");
            }
            model.removeElement(c);
            Sistema.salvarDados();
        });

        // Registrar Ausência
        btnAusente.addActionListener(e -> {
            Candidatura c = lista.getSelectedValue();
            if (c == null) return;

            boolean ok = Sistema.controladorCandidatura.registrarParticipacao(c, prof, false);

            if (ok) {
                Sistema.salvarDados();
                JOptionPane.showMessageDialog(this, "Aluno marcado como AUSENTE.");
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível registrar ausência.");
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPAEGDetalhes(prof, paeg.getAtividade(), paeg).setVisible(true);
        });
    }
}
