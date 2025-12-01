package view;

import model.Professor;
import javax.swing.*;
import java.awt.*;

public class TelaHomeProfessor extends JFrame {

    private Professor professor;

    public TelaHomeProfessor(Professor professor) {

        this.professor = professor;

        setTitle("Área do Professor");
        setSize(400, 300); // Aumentando a altura para acomodar o cabeçalho
        setLocationRelativeTo(null);
        // setLayout(new GridLayout(3, 1, 10, 10)); <-- REMOVIDO!

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Margem externa
        add(root);

        // ===================================
        // 2. CABEÇALHO (NORTE)
        // ===================================
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Adiciona um rótulo de boas-vindas
        JLabel lbl = new JLabel("Bem-vindo(a), Prof. " + professor.getNome());
        lbl.setFont(new Font("Arial", Font.BOLD, 16)); // Estiliza o texto
        painelTopo.add(lbl);

        root.add(painelTopo, BorderLayout.NORTH);

        // ===================================
        // 3. BOTÕES PRINCIPAIS (CENTER)
        // ===================================
        // Usamos FlowLayout.CENTER para agrupar os botões de função no centro
        JPanel painelBotoesPrincipais = new JPanel(new GridLayout(2, 1, 10, 10)); // 2 linhas, 1 coluna

        JButton btnNovoProjeto = new JButton("Adicionar Novo Projeto");
        JButton btnProjetos = new JButton("Projetos");

        painelBotoesPrincipais.add(btnNovoProjeto);
        painelBotoesPrincipais.add(btnProjetos);

        root.add(painelBotoesPrincipais, BorderLayout.CENTER);

        // ===================================
        // 4. BOTÃO SAIR (SUL)
        // ===================================
        // Usa FlowLayout.CENTER para centralizar o botão na largura total da região SOUTH
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSair = new JButton("Sair");
        btnSair.setPreferredSize(new Dimension(150, 30)); // Define um tamanho fixo para o botão
        painelRodape.add(btnSair);

        root.add(painelRodape, BorderLayout.SOUTH);

        btnNovoProjeto.addActionListener(e -> {
            dispose();
            new TelaCadastrarProjeto(this.professor).setVisible(true);
        });

        btnProjetos.addActionListener(e -> {
            dispose();
            new TelaListarProjetos(this.professor).setVisible(true);
        });

        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaHomeProfessor.this);
            }
        }
        );
    }
}
