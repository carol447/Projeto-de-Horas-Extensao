package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.Candidatura;
import model.PAEG;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaMinhasCandidaturas extends JFrame {

    public TelaMinhasCandidaturas(Aluno aluno) {

        setTitle("Minhas Candidaturas");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        List<Candidatura> lista = new ArrayList<>();

        for (Candidatura c : Sistema.controladorCandidatura.visualizarMinhasCandidaturas(aluno)) {
            if (c.isPendente() && c.getPaeg().isPeriodoInscricaoAberto()) {
                lista.add(c);
            }
        }

        DefaultListModel<Candidatura> model = new DefaultListModel<>();
        lista.forEach(model::addElement);

        JList<Candidatura> jlist = new JList<>(model);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adiciona borda com título
        JScrollPane scrollPane = new JScrollPane(jlist);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Candidaturas Pendentes (Período Aberto)"));
        root.add(scrollPane, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JButton btnCancelar = new JButton("Cancelar Candidatura");
        JButton btnVoltar = new JButton("Voltar");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Dimension buttonSizeCancelar = new Dimension(180, 30);
        Dimension buttonSizeVoltar = new Dimension(100, 30);

        btnCancelar.setPreferredSize(buttonSizeCancelar);
        btnVoltar.setPreferredSize(buttonSizeVoltar);

        botoes.add(btnCancelar);
        botoes.add(btnVoltar);
        root.add(botoes, BorderLayout.SOUTH);

        // ============================================================
        // CANCELAR CANDIDATURA (UC17)
        // ============================================================
        btnCancelar.addActionListener(e -> {

            Candidatura c = jlist.getSelectedValue();
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma candidatura.");
                return;
            }

            if (!c.isPendente()) {
                JOptionPane.showMessageDialog(this,
                        "Esta candidatura já foi avaliada. Não é possível cancelar.");
                return;
            }

            PAEG paeg = c.getPaeg();
            Date agora = new Date();

            if (agora.after(paeg.getDataFinalCandidatura())) {
                JOptionPane.showMessageDialog(this,
                        "O período de candidaturas já terminou. Não é possível cancelar.");
                return;
            }

            // Confirmar
            int r = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente cancelar esta candidatura?",
                    "Confirmar Cancelamento",
                    JOptionPane.YES_NO_OPTION
            );

            if (r == JOptionPane.YES_OPTION) {

                // chama o controlador
                Sistema.controladorCandidatura.cancelarCandidatura(c);

                // remover da lista da tela imediatamente
                model.removeElement(c);

                JOptionPane.showMessageDialog(this, "Candidatura cancelada com sucesso.");
            }
        });

        // VOLTAR
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeAluno(aluno).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaMinhasCandidaturas.this);
            }
        }
        );
    }
}
