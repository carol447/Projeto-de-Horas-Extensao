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
import javax.swing.border.EmptyBorder;

public class TelaListarCandidaturasPAEG extends JFrame {

    public TelaListarCandidaturasPAEG(Professor prof, PAEG paeg) {

        setTitle("Candidaturas - " + paeg.getNome());
        setSize(550, 400); // Aumentado um pouco
        setLocationRelativeTo(null);
        // setLayout(new BorderLayout()); <— Será usado no root

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // === BUSCAR PENDENTES ===
        List<Candidatura> candidaturas
                = Sistema.catalogoCandidatura.buscarPendentesPorPAEG(paeg);

        DefaultListModel<Candidatura> model = new DefaultListModel<>();
        for (Candidatura c : candidaturas) {
            model.addElement(c); // usa toString()
        }

        JList<Candidatura> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fonte para melhor visualização

        // Adiciona borda com título
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Candidaturas Pendentes para Avaliação"));
        root.add(scrollPane, BorderLayout.CENTER);

        // === BOTÕES ===
        JButton btnAprovar = new JButton("Aprovar");
        JButton btnReprovar = new JButton("Reprovar");
        JButton btnVoltar = new JButton("Voltar");

        // Centraliza botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Dimension buttonSize = new Dimension(120, 30);
        btnAprovar.setPreferredSize(buttonSize);
        btnReprovar.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        botoes.add(btnAprovar);
        botoes.add(btnReprovar);
        botoes.add(btnVoltar);

        root.add(botoes, BorderLayout.SOUTH);

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
                        "Não é possível aprovar. Limite de vagas atingido ou Período de Candidatura não encerrado.");
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
                        "Erro ao reprovar. Período de Candidatura não encerrado ou professor não autorizado.");
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

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaListarCandidaturasPAEG.this);
            }
        }
        );
    }
}
