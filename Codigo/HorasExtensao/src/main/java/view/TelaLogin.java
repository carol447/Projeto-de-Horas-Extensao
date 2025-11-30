package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorAluno;
import controller.ControladorProfessor;
import model.Aluno;
import model.Professor;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    public TelaLogin() {
        setTitle("Sistema Horas de Extensão - Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel lblEmail = new JLabel("E-mail:", SwingConstants.CENTER);
        JTextField txtEmail = new JTextField();

        JLabel lblSenha = new JLabel("Senha:", SwingConstants.CENTER);
        JPasswordField txtSenha = new JPasswordField();

        JButton btnEntrar = new JButton("Entrar");
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEncerrar = new JButton("Encerrar Sistema");

        add(lblEmail);
        add(txtEmail);
        add(lblSenha);
        add(txtSenha);
        add(btnEntrar);
        add(btnRegistrar);
        add(btnEncerrar);

        // ============= EVENTO DE LOGIN =============
        btnEntrar.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String senha = String.valueOf(txtSenha.getPassword());

            ControladorAluno cAluno = Sistema.controladorAluno;
            ControladorProfessor cProfessor = Sistema.controladorProfessor;

            // Tenta autenticar como Aluno
            Aluno aluno = cAluno.autenticarAluno(email, senha);
            if (aluno != null) {
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + aluno.getNome());
                dispose();
                // abrir tela home do aluno
                new TelaHomeAluno(aluno).setVisible(true);
                return;
            }

            // Tenta autenticar como Professor
            Professor professorTemp = cProfessor.autenticarProf(email, senha);
            if (professorTemp != null) {
                // Recuperar o "professor real" do catálogo (mesma instância usada em Projetos)
                Professor professorReal = Sistema.catalogoProfessor.encontrarProfPorCpf(professorTemp.getCpf());

                JOptionPane.showMessageDialog(this, "Bem-vindo, Professor " + professorReal.getNome());
                new TelaHomeProfessor(professorReal).setVisible(true);
                dispose();
                return;
            }

            // Se não autenticou
            JOptionPane.showMessageDialog(this, "E-mail ou senha incorretos.");
        });

        // ============= EVENTO DE REGISTRO =============
        btnRegistrar.addActionListener(e -> {
            dispose();
            new TelaCadastroEscolha().setVisible(true);
        });

        // ============= ENCERRAR SISTEMA =============
        btnEncerrar.addActionListener(e -> {
            Sistema.salvarDados();
            System.exit(0);
        });
    }
}
