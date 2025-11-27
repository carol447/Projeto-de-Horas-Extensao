package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controller.ControladorProjeto;
import controller.ControladorAtividade;
import model.Professor;
import model.Projeto;
import java.util.List;

/**
 * UC07 - Tela de Consulta de Projetos do Professor
 * Lista todos os projetos onde o professor é coordenador
 */
public class TelaMeusProjetos extends JFrame {
    private ControladorProjeto controladorProjeto;
    private ControladorAtividade controladorAtividade;
    private Professor professorLogado;
    private JTable tabelaProjetos;
    private DefaultTableModel modeloTabela;
    
    public TelaMeusProjetos(ControladorProjeto controladorProjeto, 
                           ControladorAtividade controladorAtividade,
                           Professor professorLogado) {
        this.controladorProjeto = controladorProjeto;
        this.controladorAtividade = controladorAtividade;
        this.professorLogado = professorLogado;
        
        setTitle("Meus Projetos de Extensão");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Meus Projetos de Extensão", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Tabela de projetos
        String[] colunas = {"Título", "Status", "Data Início", "Data Fim", "Equipe", "Atividades"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProjetos = new JTable(modeloTabela);
        tabelaProjetos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProjetos.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(tabelaProjetos);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton btnNovo = new JButton("Novo Projeto");
        btnNovo.setFont(new Font("Arial", Font.PLAIN, 14));
        btnNovo.addActionListener(e -> abrirTelaCriarProjeto());
        
        JButton btnAbrir = new JButton("Abrir Projeto");
        btnAbrir.setFont(new Font("Arial", Font.PLAIN, 14));
        btnAbrir.addActionListener(e -> abrirProjetoSelecionado());
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnAtualizar.addActionListener(e -> carregarProjetos());
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFechar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnAbrir);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        // Carregar projetos iniciais
        carregarProjetos();
    }
    
    private void carregarProjetos() {
        modeloTabela.setRowCount(0);
        List<Projeto> projetos = controladorProjeto.consultarMeusProjetos(professorLogado);
        
        for (Projeto p : projetos) {
            Object[] linha = {
                p.getTitulo(),
                p.getStatus(),
                p.getDataInicio(),
                p.getDataFim(),
                p.getTamanhoEquipe() + " professor(es)",
                p.getTotalAtividades() + " atividade(s)"
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void abrirTelaCriarProjeto() {
        TelaCriarProjeto tela = new TelaCriarProjeto(controladorProjeto, professorLogado);
        tela.setVisible(true);
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarProjetos();
            }
        });
    }
    
    private void abrirProjetoSelecionado() {
        int linhaSelecionada = tabelaProjetos.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um projeto para abrir.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Projeto> projetos = controladorProjeto.consultarMeusProjetos(professorLogado);
        Projeto projetoSelecionado = projetos.get(linhaSelecionada);
        
        TelaDetalhesProjeto tela = new TelaDetalhesProjeto(
            controladorProjeto, 
            controladorAtividade,
            projetoSelecionado,
            professorLogado
        );
        tela.setVisible(true);
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarProjetos();
            }
        });
    }
}