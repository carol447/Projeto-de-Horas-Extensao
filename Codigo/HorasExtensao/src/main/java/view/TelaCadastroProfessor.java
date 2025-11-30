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
        setLayout(new GridLayout(7, 2, 10, 10));

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JComboBox<Cursos> cbCurso = new JComboBox<>(Cursos.values());
        JButton btnCadastrar = new JButton("Cadastrar");

        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("CPF:")); add(txtCpf);
        add(new JLabel("E-mail:")); add(txtEmail);
        add(new JLabel("Senha:")); add(txtSenha);
        add(new JLabel("Curso:")); add(cbCurso);
        add(btnCadastrar);

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
    }
}
