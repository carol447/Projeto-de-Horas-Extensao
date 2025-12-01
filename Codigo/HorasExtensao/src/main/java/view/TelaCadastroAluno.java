package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorAluno;
import model.Cursos;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroAluno extends JFrame {

    public TelaCadastroAluno() {
        setTitle("Cadastro Aluno");
        setSize(400, 400);
        setLocationRelativeTo(null);
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margem externa
        add(root);

        // ============================================
        // Painel do Formulário (usando GridLayout)
        // ============================================
        // 6 linhas (Nome, CPF, RA, Email, Senha, Curso) e 2 colunas
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtRa = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JComboBox<Cursos> cbCurso = new JComboBox<>(Cursos.values());
        JButton btnCadastrar = new JButton("Cadastrar"); // Declarado aqui

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("CPF:"));
        formPanel.add(txtCpf);
        formPanel.add(new JLabel("RA:"));
        formPanel.add(txtRa);
        formPanel.add(new JLabel("E-mail:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Senha:"));
        formPanel.add(txtSenha);
        formPanel.add(new JLabel("Curso:"));
        formPanel.add(cbCurso);

        root.add(formPanel, BorderLayout.CENTER); // Adiciona o formulário ao centro

        // ============================================
        // Painel do Botão (Centralizado)
        // ============================================
        // Usa FlowLayout.CENTER para centralizar o botão na largura total da região SOUTH
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.add(btnCadastrar);

        root.add(painelBotao, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> {
            ControladorAluno ca = Sistema.controladorAluno;

            boolean ok = ca.registrarAluno(
                    txtNome.getText(),
                    txtCpf.getText(),
                    txtRa.getText(),
                    txtEmail.getText(),
                    new String(txtSenha.getPassword()),
                    (Cursos) cbCurso.getSelectedItem()
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Aluno cadastrado!");
                Sistema.salvarDados();
                dispose();
                new TelaLogin().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erro no cadastro. Verifique os dados.");
            }
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaCadastroAluno.this);
            }
        }
        );
    }
}
