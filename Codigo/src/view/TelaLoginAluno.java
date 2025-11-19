package view;

import javax.swing.*;
import java.awt.*;
import controller.ControladorAluno;
import model.Aluno;

/**
 * UC05 - Tela de Login de Aluno
 */
public class TelaLoginAluno extends JFrame {
    private ControladorAluno controlador;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    
    public TelaLoginAluno(ControladorAluno controlador) {
        this.controlador = controlador;
        
        setTitle("Login Aluno");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Login Aluno", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new GridLayout(2, 2, 10, 15));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        painelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painelFormulario.add(txtEmail);
        
        painelFormulario.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painelFormulario.add(txtSenha);
        
        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        
        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnEntrar.addActionListener(e -> autenticar());
        painelBotoes.add(btnEntrar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void autenticar() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        
        Aluno aluno = controlador.autenticarAluno(email, senha);
        
        if (aluno != null) {
            JOptionPane.showMessageDialog(this, 
                "Login realizado com sucesso!\nBem-vindo(a), " + aluno.getNome() + "!\nHoras acumuladas: " + aluno.getHorasAcumuladas(), 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Email ou senha incorretos.", 
                "Erro de Autenticação", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtEmail.setText("");
        txtSenha.setText("");
    }
}
