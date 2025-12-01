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
        setSize(420, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // ============================
        // Layout principal
        // ============================
        JPanel root = new JPanel(new BorderLayout(10, 0));
        root.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        add(root);

        // ============================
        // Painel do formulário
        // ============================
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblEmail = new JLabel("E-mail:");
        JTextField txtEmail = new JTextField(20);

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField(20);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnRegistrar = new JButton("Registrar");

        // Linha 1 — Email
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        form.add(lblEmail, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        form.add(txtEmail, c);

        // Linha 2 — Senha
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        form.add(lblSenha, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        form.add(txtSenha, c);

        // Linha 3 — Botões lado a lado
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnRegistrar);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        form.add(painelBotoes, c);

        root.add(form, BorderLayout.CENTER);

        // ============================
        // Painel do botão "Encerrar"
        // ============================
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        rodape.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton btnEncerrar = new JButton("Encerrar Sistema");
        rodape.add(btnEncerrar);

        root.add(rodape, BorderLayout.SOUTH);

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
            util.EncerrarSistema.encerrarAplicacao(TelaLogin.this);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaLogin.this);
            }
        });
    }
}
