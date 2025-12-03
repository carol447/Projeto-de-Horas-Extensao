package view;

import com.mycompany.horasextensao.Sistema;
import model.Atividade;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaAtividadeDetalhes extends JFrame {

    public TelaAtividadeDetalhes(Professor prof, Projeto projeto, Atividade atividade) {

        setTitle("Atividade: " + atividade.getNome());
        setSize(600, 380);
        setLocationRelativeTo(null);
        // setLayout(new BorderLayout()); <— Não é mais necessário, será usado no root

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. PAINEL DE INFORMAÇÕES (CENTER)
        // ===================================
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Alinha tudo ao topo/esquerda

        // --- NOME DA ATIVIDADE ---
        JLabel lblNome = new JLabel("<html><b>Nome:</b> " + atividade.getNome() + "</html>");
        lblNome.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(lblNome, gbc);

        // --- DESCRIÇÃO ---
        JTextArea txtDescricao = new JTextArea(atividade.getDescricao());
        txtDescricao.setEditable(false);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setBackground(infoPanel.getBackground());
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBorder(BorderFactory.createTitledBorder("Descrição"));

        gbc.gridy = 1;
        gbc.weighty = 1.0; // Ocupa a maior parte do espaço vertical
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(scrollDescricao, gbc);

        // --- PAEGs CADASTRADOS ---
        JLabel lblPaegs = new JLabel("<html><b>PAEGs cadastrados:</b> <span style='color: green;'>" + atividade.getPaegs().size() + "</span></html>");
        lblPaegs.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        gbc.weighty = 0.0; // Não estica verticalmente
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(lblPaegs, gbc);

        root.add(new JScrollPane(infoPanel), BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JButton btnPAEGs = new JButton("Ver PAEGs"); // Renomeado
        JButton btnAdicionarPAEG = new JButton("Adicionar PAEG");
        JButton btnExcluir = new JButton("Excluir Atividade");
        JButton btnVoltar = new JButton("Voltar");

        // Centraliza e adiciona espaçamento aos botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));

        Dimension buttonSize = new Dimension(125, 30);
        btnPAEGs.setPreferredSize(buttonSize);
        btnAdicionarPAEG.setPreferredSize(buttonSize);
        btnExcluir.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        painelBotoes.add(btnPAEGs);
        painelBotoes.add(btnAdicionarPAEG);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);

        root.add(painelBotoes, BorderLayout.SOUTH);

        btnPAEGs.addActionListener(e -> {
            dispose();
            new TelaListarPAEGsProfessor(prof, atividade).setVisible(true);
        });

        btnAdicionarPAEG.addActionListener(e -> {
            dispose();
            new TelaCadastrarPAEG(prof, atividade).setVisible(true);
        });

        btnExcluir.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this, "Excluir atividade?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                if (Sistema.controladorAtividade.excluirAtividade(atividade, prof)) {
                    JOptionPane.showMessageDialog(this, "Atividade excluída!");
                    new TelaListarAtividades(prof, projeto).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Há PAEGs vinculados. Não é possível excluir.");
                }
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaProjetoDetalhes(prof, projeto).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaAtividadeDetalhes.this);
            }
        }
        );
    }
}
