package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorProfessor;
import model.Cursos;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroProfessor extends JFrame {

    public TelaCadastroProfessor() {
        setTitle("Cadastro Professor");
        setSize(400, 400);
        setLocationRelativeTo(null);
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona margem externa
        add(root);

        // ============================================
        // Painel do Formulário (usando o GridLayout)
        // ============================================
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Apenas 5 linhas para os campos

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JComboBox<Cursos> cbCurso = new JComboBox<>(Cursos.values());
        
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("CPF:"));
        formPanel.add(txtCpf);
        formPanel.add(new JLabel("E-mail:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Senha:"));
        formPanel.add(txtSenha);
        formPanel.add(new JLabel("Curso:"));
        formPanel.add(cbCurso);
        
        root.add(formPanel, BorderLayout.CENTER);

        // ============================================
        // Painel do Botão (Centralizado)
        // ============================================
        // Usa FlowLayout.CENTER para centralizar o botão na largura total.
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCadastrar = new JButton("Cadastrar");
        painelBotao.add(btnCadastrar);
        
        // Adiciona o painel do botão na parte sul do BorderLayout, ocupando a largura total.
        root.add(painelBotao, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> {
            ControladorProfessor cp = Sistema.controladorProfessor;

            boolean ok = cp.registrarProf(
                    txtNome.getText(),
                    txtCpf.getText(),
                    txtEmail.getText(),
                    new String(txtSenha.getPassword()),
                    (Cursos) cbCurso.getSelectedItem()
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Professor cadastrado!");
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
                util.EncerrarSistema.encerrarAplicacao(TelaCadastroProfessor.this);
            }
        }
        );
    }
}
