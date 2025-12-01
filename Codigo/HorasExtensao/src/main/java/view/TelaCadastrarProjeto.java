package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorProjeto;
import model.Cursos;
import model.Professor;

import javax.swing.*;
import java.awt.*;

public class TelaCadastrarProjeto extends JFrame {

    public TelaCadastrarProjeto(Professor professor) {
        setTitle("Cadastrar Projeto"); // 
        setSize(500, 450); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. PAINEL DO FORMULÁRIO (CENTER)
        // ===================================
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Espaçamento entre componentes
        c.fill = GridBagConstraints.HORIZONTAL; // Faz os campos preencherem o espaço horizontal

        JTextField txtTitulo = new JTextField(20);

        // Usamos JTextArea com JScrollPane para a Descrição
        JTextArea txtDescricao = new JTextArea(5, 20); // 5 linhas visíveis
        txtDescricao.setLineWrap(true); // Quebra de linha automática
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JComboBox<Cursos> cbCurso = new JComboBox<>(Cursos.values());

        // Linha 1: Título
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0; // Rótulo: sem estiramento horizontal
        formPanel.add(new JLabel("Título:"), c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1; // Campo: ocupa o resto do espaço
        formPanel.add(txtTitulo, c);

        // Linha 2: Descrição (Ocupa a altura da JTextArea)
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.anchor = GridBagConstraints.NORTHWEST; // Alinha o rótulo ao topo
        formPanel.add(new JLabel("Descrição:"), c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1; // Estira verticalmente
        c.fill = GridBagConstraints.BOTH; // Estira em ambas as direções
        formPanel.add(scrollDescricao, c);

        // Linha 3: Curso
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 0; // Reseta o estiramento vertical
        c.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(new JLabel("Curso:"), c);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        formPanel.add(cbCurso, c);

        root.add(formPanel, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5)); // Centraliza e espaça

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        Dimension buttonSize = new Dimension(100, 30);
        btnSalvar.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);

        root.add(painelBotoes, BorderLayout.SOUTH);
        btnSalvar.addActionListener(e -> {
            ControladorProjeto cp = Sistema.controladorProjeto;

            boolean ok = cp.criarProjeto(
                    txtTitulo.getText(),
                    txtDescricao.getText(),
                    ((Cursos) cbCurso.getSelectedItem()).name(),
                    professor
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Projeto cadastrado!");
                Sistema.salvarDados();
                dispose();
                new TelaHomeProfessor(professor).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar projeto.");
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeProfessor(professor).setVisible(true);
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaCadastrarProjeto.this);
            }
        }
        );
    }
}
