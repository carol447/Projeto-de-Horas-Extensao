package view;

import javax.swing.*;
import java.awt.*;
import controller.ControladorProfessor;
import controller.ControladorAluno;
import controller.ControladorProjeto;
import controller.ControladorAtividade;
import repository.CatalogoProfessor;
import repository.CatalogoAluno;
import repository.CatalogoProjeto;
import persistence.GerenciadorPersistencia;

/**
 * UC01 - Tela Inicial do Sistema
 * Menu principal com opções de registro e login para professores e alunos.
 */
public class TelaInicial extends JFrame {
    private CatalogoProfessor catalogoProfessor;
    private CatalogoAluno catalogoAluno;
    private CatalogoProjeto catalogoProjeto;
    private ControladorProfessor controladorProfessor;
    private ControladorAluno controladorAluno;
    private ControladorProjeto controladorProjeto;
    private ControladorAtividade controladorAtividade;
    
    public TelaInicial() {
        // Carregar dados persistidos
        carregarDados();
        
        // Configurar janela
        setTitle("Sistema de Gerenciamento de Horas de Extensão");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Criar painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Sistema de Horas de Extensão", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(6, 1, 10, 15));
        
        // Botões para professores
        JLabel lblProfessor = new JLabel("Professor:", SwingConstants.CENTER);
        lblProfessor.setFont(new Font("Arial", Font.BOLD, 16));
        painelBotoes.add(lblProfessor);
        
        JButton btnRegistrarProfessor = new JButton("Registrar Professor");
        btnRegistrarProfessor.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegistrarProfessor.addActionListener(e -> abrirTelaRegistroProfessor());
        painelBotoes.add(btnRegistrarProfessor);
        
        JButton btnLoginProfessor = new JButton("Login Professor");
        btnLoginProfessor.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLoginProfessor.addActionListener(e -> abrirTelaLoginProfessor());
        painelBotoes.add(btnLoginProfessor);
        
        // Botões para alunos
        JLabel lblAluno = new JLabel("Aluno:", SwingConstants.CENTER);
        lblAluno.setFont(new Font("Arial", Font.BOLD, 16));
        painelBotoes.add(lblAluno);
        
        JButton btnRegistrarAluno = new JButton("Registrar Aluno");
        btnRegistrarAluno.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegistrarAluno.addActionListener(e -> abrirTelaRegistroAluno());
        painelBotoes.add(btnRegistrarAluno);
        
        JButton btnLoginAluno = new JButton("Login Aluno");
        btnLoginAluno.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLoginAluno.addActionListener(e -> abrirTelaLoginAluno());
        painelBotoes.add(btnLoginAluno);
        
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        
        // Rodapé
        JLabel lblRodape = new JLabel("UNESP - Engenharia de Software II", SwingConstants.CENTER);
        lblRodape.setFont(new Font("Arial", Font.ITALIC, 12));
        painelPrincipal.add(lblRodape, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        // Adicionar WindowListener para salvar dados ao fechar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                salvarDados();
            }
        });
    }
    
    private void carregarDados() {
        try {
            catalogoProfessor = GerenciadorPersistencia.getCatalogoProfessor();
            catalogoAluno = GerenciadorPersistencia.getCatalogoAluno();
            catalogoProjeto = GerenciadorPersistencia.getCatalogoProjeto();
            controladorProfessor = new ControladorProfessor(catalogoProfessor);
            controladorAluno = new ControladorAluno(catalogoAluno);
            controladorProjeto = new ControladorProjeto(catalogoProjeto, catalogoProfessor);
            controladorAtividade = new ControladorAtividade();
        } catch (Exception e) {
            catalogoProfessor = new CatalogoProfessor();
            catalogoAluno = new CatalogoAluno();
            catalogoProjeto = new CatalogoProjeto();
            controladorProfessor = new ControladorProfessor(catalogoProfessor);
            controladorAluno = new ControladorAluno(catalogoAluno);
            controladorProjeto = new ControladorProjeto(catalogoProjeto, catalogoProfessor);
            controladorAtividade = new ControladorAtividade();
        }
    }
    
    private void salvarDados() {
        GerenciadorPersistencia.salvarDados(catalogoProfessor, catalogoAluno, catalogoProjeto);
    }
    
    private void abrirTelaRegistroProfessor() {
        TelaRegistroProfessor tela = new TelaRegistroProfessor(controladorProfessor);
        tela.setVisible(true);
    }
    
    private void abrirTelaLoginProfessor() {
        TelaLoginProfessor tela = new TelaLoginProfessor(controladorProfessor, controladorProjeto, controladorAtividade);
        tela.setVisible(true);
    }
    
    private void abrirTelaRegistroAluno() {
        TelaRegistroAluno tela = new TelaRegistroAluno(controladorAluno);
        tela.setVisible(true);
    }
    
    private void abrirTelaLoginAluno() {
        TelaLoginAluno tela = new TelaLoginAluno(controladorAluno);
        tela.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaInicial tela = new TelaInicial();
            tela.setVisible(true);
        });
    }
}
