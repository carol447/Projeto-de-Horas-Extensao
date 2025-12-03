package view;

import model.Aluno;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaHorasDeExtensao extends JFrame {

    public TelaHorasDeExtensao(Aluno aluno) {

        setTitle("Minhas Horas de Extensão");
        setSize(450, 280);
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(root);

        // ===================================
        // 2. PAINEL DE INFORMAÇÕES (CENTER)
        // ===================================
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JLabel lblTitulo = new JLabel("Horas de Extensão Acumuladas:", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblHoras = new JLabel(aluno.getHorasAcumuladas() + " horas", SwingConstants.CENTER);
        // Destaque para o valor
        lblHoras.setFont(new Font("Arial", Font.BOLD, 36));
        lblHoras.setForeground(new Color(0, 102, 204));

        infoPanel.add(lblTitulo);
        infoPanel.add(lblHoras);

        root.add(infoPanel, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÃO VOLTAR (SOUTH)
        // ===================================
        JButton btnVoltar = new JButton("Voltar");
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        Dimension buttonSize = new Dimension(150, 35);
        btnVoltar.setPreferredSize(buttonSize);

        painelBotoes.add(btnVoltar);

        root.add(painelBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeAluno(aluno).setVisible(true);
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaHorasDeExtensao.this);
            }
        }
        );
    }
}
