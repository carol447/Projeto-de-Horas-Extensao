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
        setLayout(new GridLayout(8, 2, 10, 10));

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtRa = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JComboBox<Cursos> cbCurso = new JComboBox<>(Cursos.values());
        JButton btnCadastrar = new JButton("Cadastrar");

        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("CPF:")); add(txtCpf);
        add(new JLabel("RA:")); add(txtRa);
        add(new JLabel("E-mail:")); add(txtEmail);
        add(new JLabel("Senha:")); add(txtSenha);
        add(new JLabel("Curso:")); add(cbCurso);
        add(btnCadastrar);

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
    }
}
