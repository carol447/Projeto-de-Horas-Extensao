package view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import controller.ControladorProjeto;
import model.Professor;

/**
 * Tela de Criação de Projeto
 */
public class TelaCriarProjeto extends JFrame {
    private ControladorProjeto controlador;
    private Professor professorLogado;
    
    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JSpinner dateInicioSpinner;
    private JSpinner dateFimSpinner;
    
    public TelaCriarProjeto(ControladorProjeto controlador, Professor professorLogado) {
        this.controlador = controlador;
        this.professorLogado = professorLogado;
        
        setTitle("Criar Novo Projeto");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Criar Novo Projeto de Extensão", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Formulário
        JPanel painelForm = new JPanel();
        painelForm.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título do Projeto
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelForm.add(new JLabel("Título:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTitulo = new JTextField();
        painelForm.add(txtTitulo, gbc);
        
        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        painelForm.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescricao = new JTextArea(5, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        painelForm.add(scrollDesc, gbc);
        
        // Data Início
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(new JLabel("Data Início:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        SpinnerDateModel modelInicio = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        dateInicioSpinner = new JSpinner(modelInicio);
        dateInicioSpinner.setEditor(new JSpinner.DateEditor(dateInicioSpinner, "dd/MM/yyyy"));
        painelForm.add(dateInicioSpinner, gbc);
        
        // Data Fim
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        painelForm.add(new JLabel("Data Fim:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        SpinnerDateModel modelFim = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        dateFimSpinner = new JSpinner(modelFim);
        dateFimSpinner.setEditor(new JSpinner.DateEditor(dateFimSpinner, "dd/MM/yyyy"));
        painelForm.add(dateFimSpinner, gbc);
        
        // Coordenador (read-only)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        painelForm.add(new JLabel("Coordenador:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField txtCoordenador = new JTextField(professorLogado.getNome());
        txtCoordenador.setEditable(false);
        txtCoordenador.setBackground(Color.LIGHT_GRAY);
        painelForm.add(txtCoordenador, gbc);
        
        painelPrincipal.add(painelForm, BorderLayout.CENTER);
        
        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton btnCriar = new JButton("Criar Projeto");
        btnCriar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCriar.addActionListener(e -> criarProjeto());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnCriar);
        painelBotoes.add(btnCancelar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void criarProjeto() {
        String titulo = txtTitulo.getText().trim();
        String descricao = txtDescricao.getText().trim();
        Date dataInicio = (Date) dateInicioSpinner.getValue();
        Date dataFim = (Date) dateFimSpinner.getValue();
        
        String resultado = controlador.criarProjeto(titulo, descricao, dataInicio, dataFim, professorLogado);
        
        if (resultado.equals("Projeto criado com sucesso!")) {
            JOptionPane.showMessageDialog(this, resultado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}