package view;

import com.mycompany.horasextensao.Sistema;
import model.Candidatura;
import model.PAEG;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaRegistrarParticipacao extends JFrame {

    public TelaRegistrarParticipacao(Professor prof, PAEG paeg) {

        setTitle("Registrar Participação - " + paeg.getNome());
        setSize(550, 400);
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15)); // Margens externas
        add(root);

        // ===================================
        // 2. LISTA DE APROVADOS (CENTER)
        // ===================================
        // Apenas APROVADOS (UC14 pré-condição)
        List<Candidatura> aprovados = Sistema.catalogoCandidatura.buscarAprovadosPorPAEG(paeg)
                .stream()
                .filter(Candidatura::isAprovada)
                .toList();

        DefaultListModel<Candidatura> model = new DefaultListModel<>();
        aprovados.forEach(model::addElement);

        JList<Candidatura> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Adiciona borda com título
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Candidatos Aprovados (Aguardando Registro)"));
        root.add(scrollPane, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES DE COMANDO (SOUTH)
        // ===================================
        JButton btnPresente = new JButton("Marcar Presente");
        JButton btnAusente = new JButton("Marcar Ausente");
        JButton btnVoltar = new JButton("Voltar");

        // Painel para centralizar os botões
        JPanel comandos = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        // Define o tamanho padronizado dos botões
        Dimension buttonSize = new Dimension(150, 30);
        btnPresente.setPreferredSize(buttonSize);
        btnAusente.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(new Dimension(100, 30)); // Voltar menor

        comandos.add(btnPresente);
        comandos.add(btnAusente);
        comandos.add(btnVoltar);

        root.add(comandos, BorderLayout.SOUTH);

        btnPresente.addActionListener(e -> {
            Candidatura c = lista.getSelectedValue();
            if (c == null) {
                return;
            }

            boolean ok = Sistema.controladorCandidatura.registrarParticipacao(c, prof, true);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Participação registrada com sucesso!");
                model.removeElement(c); // Só remove se deu certo
                Sistema.salvarDados();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Ainda não é possível registrar participação.\n"
                        + " PAEG ainda está em Período de Execução ");
            }
        });

        // Registrar Ausência
        btnAusente.addActionListener(e -> {
            Candidatura c = lista.getSelectedValue();
            if (c == null) {
                return;
            }

            boolean ok = Sistema.controladorCandidatura.registrarParticipacao(c, prof, false);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Aluno marcado como AUSENTE.");
                model.removeElement(c);
                Sistema.salvarDados();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Ainda não é possível registrar participação.\n"
                        + " PAEG ainda está em Período de Execução ");
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPAEGDetalhes(prof, paeg.getAtividade(), paeg).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaRegistrarParticipacao.this);
            }
        }
        );
    }
}
