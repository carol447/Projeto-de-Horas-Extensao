package view;

import javax.swing.*;
import java.awt.*;
import controller.ControladorAluno;

/**
 * UC04 - Tela de Registro de Aluno
 */
public class TelaRegistroAluno extends JFrame {
    private ControladorAluno controlador;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtCpf;
    private JTextField txtCurso;
    private JTextField txtRa;
    
    public TelaRegistroAluno(ControladorAluno controlador) {
        this.controlador = controlador;
        
        setTitle("Registrar Aluno");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Cadastro de Aluno", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new GridLayout(6, 2, 10, 15));
        
        painelFormulario.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelFormulario.add(txtNome);
        
        painelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painelFormulario.add(txtEmail);
        
        painelFormulario.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painelFormulario.add(txtSenha);
        
        painelFormulario.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        painelFormulario.add(txtCpf);
        
        painelFormulario.add(new JLabel("Curso:"));
        txtCurso = new JTextField();
        painelFormulario.add(txtCurso);
        
        painelFormulario.add(new JLabel("RA:"));
        txtRa = new JTextField();
        painelFormulario.add(txtRa);
        
        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        
        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegistrar.addActionListener(e -> registrarAluno());
        painelBotoes.add(btnRegistrar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void registrarAluno() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String cpf = txtCpf.getText().trim();
        String curso = txtCurso.getText().trim();
        String ra = txtRa.getText().trim();
        
        String resultado = controlador.registrarAluno(nome, email, senha, cpf, curso, ra);
        
        if (resultado.equals("Aluno cadastrado com sucesso!")) {
            JOptionPane.showMessageDialog(this, resultado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
        txtCpf.setText("");
        txtCurso.setText("");
        txtRa.setText("");
    }
}
