package view;

import model.Aluno;

import javax.swing.*;
import java.awt.*;

public class TelaHomeAluno extends JFrame {

    public TelaHomeAluno(Aluno aluno) {

        setTitle("Área do Aluno");
        setSize(400, 350);
        setLocationRelativeTo(null);

        // ===================================
        // PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(root);

        // ===================================
        // CABEÇALHO (NORTE)
        // ===================================
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl = new JLabel("Bem-vindo, " + aluno.getNome(), SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16)); // Estiliza o texto
        painelTopo.add(lbl);

        root.add(painelTopo, BorderLayout.NORTH);

        // ===================================
        // CORPO DOS BOTÕES (CENTER)
        // ===================================
        JPanel painelBotoes = new JPanel(new GridLayout(3, 2, 15, 15));

        JButton btnPAEGs = new JButton("PAEGs Disponíveis");
        JButton btnMeusPAEGs = new JButton("Meus PAEGs");
        JButton btnMinhasCandidaturas = new JButton("Minhas Candidaturas");
        JButton btnHoras = new JButton("Minhas Horas");
        JButton btnNotificacoes = new JButton("Notificações");

        painelBotoes.add(btnPAEGs);
        painelBotoes.add(btnMeusPAEGs);
        painelBotoes.add(btnMinhasCandidaturas);

        painelBotoes.add(btnHoras);
        painelBotoes.add(btnNotificacoes);
        root.add(painelBotoes, BorderLayout.CENTER);
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSair = new JButton("Sair");
        btnSair.setPreferredSize(new Dimension(150, 30)); // Define um tamanho fixo
        painelRodape.add(btnSair);

        root.add(painelRodape, BorderLayout.SOUTH);

        btnPAEGs.addActionListener(e -> {
            dispose();
            new TelaListarPAEGsDisponiveis(aluno).setVisible(true);
        });

        btnMeusPAEGs.addActionListener(e -> {
            dispose();
            new TelaMeusPAEGs(aluno).setVisible(true);
        });

        btnMinhasCandidaturas.addActionListener(e -> {
            dispose();
            new TelaMinhasCandidaturas(aluno).setVisible(true);
        });

        btnHoras.addActionListener(e -> {
            dispose();
            new TelaHorasDeExtensao(aluno).setVisible(true);
        });

        btnNotificacoes.addActionListener(e -> {
            dispose();
            new TelaNotificacoesAluno(aluno).setVisible(true);
        });

        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaHomeAluno.this);
            }
        }
        );
    }
}
