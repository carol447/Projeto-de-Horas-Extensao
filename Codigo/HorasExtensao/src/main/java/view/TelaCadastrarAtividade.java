package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorAtividade;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaCadastrarAtividade extends JFrame {

    public TelaCadastrarAtividade(Professor professor, Projeto projeto) {
        setTitle("Cadastrar Atividade");
        setSize(450, 400); 
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. PAINEL DO FORMULÁRIO (CENTER)
        // ===================================
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtTitulo = new JTextField(20);

        // Descrição como JTextArea com ScrollPane
        JTextArea txtDescricao = new JTextArea(5, 20);
        txtDescricao.setLineWrap(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Linha 1: Título
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        formPanel.add(new JLabel("Título:"), c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        formPanel.add(txtTitulo, c);

        // Linha 2: Descrição
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Descrição:"), c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1; // Estira verticalmente
        c.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollDescricao, c);

        root.add(formPanel, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        Dimension buttonSize = new Dimension(100, 30);
        btnSalvar.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);

        root.add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> {
            ControladorAtividade ca = Sistema.controladorAtividade;

            boolean ok = ca.criarAtividade(
                    txtTitulo.getText(),
                    txtDescricao.getText(),
                    projeto
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Atividade criada!");
                Sistema.salvarDados();
                dispose();
                new TelaProjetoDetalhes(professor, projeto).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar atividade.");
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaProjetoDetalhes(professor, projeto).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaCadastrarAtividade.this);
            }
        }
        );
    }
}
