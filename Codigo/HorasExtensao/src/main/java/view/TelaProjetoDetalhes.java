package view;

import model.Professor;
import model.Projeto;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaProjetoDetalhes extends JFrame {

    public TelaProjetoDetalhes(Professor prof, Projeto projeto) {

        setTitle("Projeto: " + projeto.getNome());
        setSize(650, 450);
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. PAINEL DE INFORMAÇÕES (CENTER) - Conteúdo mantido
        // ===================================
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- TÍTULO ---
        JLabel lblTitulo = new JLabel("<html><b>Nome:</b> " + projeto.getNome() + "</html>");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(lblTitulo, gbc);

        // --- CURSO ---
        JLabel lblCurso = new JLabel("<html><b>Curso:</b> <span style='color: blue;'>" + projeto.getCurso() + "</span></html>");
        lblCurso.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        infoPanel.add(lblCurso, gbc);

        // --- SEPARADOR (Espaçador) ---
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
        gbc.insets = new Insets(5, 0, 5, 0);

        // --- DESCRIÇÃO ---
        JTextArea txtDescricao = new JTextArea(projeto.getDescricao());
        txtDescricao.setEditable(false);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setBackground(infoPanel.getBackground());
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBorder(BorderFactory.createTitledBorder("Descrição do Projeto"));
        gbc.gridy = 3;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(scrollDescricao, gbc);

        // --- PROFESSORES ---
        StringBuilder sbProfessores = new StringBuilder();
        for (Professor p : projeto.getProfessores()) {
            sbProfessores.append("<li>").append(p.getNome()).append(" (").append(p.getEmail()).append(")").append("</li>");
        }
        JLabel lblProfessores = new JLabel(
                "<html><b>Professores envolvidos:</b><br/><ul>" + sbProfessores.toString() + "</ul></html>"
        );
        lblProfessores.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        gbc.gridy = 4;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(lblProfessores, gbc);

        root.add(new JScrollPane(infoPanel), BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH) - AGORA COM 4 BOTÕES
        // ===================================
        JButton btnAtividades = new JButton("Ver Atividades");
        JButton btnAdicionar = new JButton("Adicionar Atividade");
        JButton btnGerenciar = new JButton("Gerenciar Membros"); // NOVO BOTÃO
        JButton btnVoltar = new JButton("Voltar");

        // Centraliza e adiciona espaçamento aos botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        // Ajuste o tamanho para caber 4 botões
        Dimension buttonSize = new Dimension(140, 30);
        btnAdicionar.setPreferredSize(buttonSize);
        btnAtividades.setPreferredSize(buttonSize);
        btnGerenciar.setPreferredSize(buttonSize); // Define o tamanho
        btnVoltar.setPreferredSize(buttonSize);

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtividades);
        painelBotoes.add(btnGerenciar); // Adiciona o novo botão
        painelBotoes.add(btnVoltar);

        root.add(painelBotoes, BorderLayout.SOUTH);

        // ===================================
        // LISTENERS
        // ===================================
        btnAdicionar.addActionListener(e -> { // NOVO LISTENER
            dispose();
            new TelaCadastrarAtividade(prof, projeto).setVisible(true);
        });

        btnAtividades.addActionListener(e -> {
            dispose();
            new TelaListarAtividades(prof, projeto).setVisible(true);
        });

        btnGerenciar.addActionListener(e -> { // NOVO LISTENER
            dispose();
            new TelaGerenciarProfessores(prof, projeto).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaListarProjetos(prof).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaProjetoDetalhes.this);
            }
        }
        );
    }
}
