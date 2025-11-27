package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import controller.ControladorProjeto;
import controller.ControladorAtividade;
import model.Projeto;
import model.Professor;
import model.Atividade;
import repository.CatalogoProfessor;

/**
 * Tela de Detalhes do Projeto
 * UC08 - Atribuir Professor à Equipe
 * UC09 - Adicionar Atividade
 * UC10 - Excluir Atividade
 * UC11 - Excluir Projeto
 */
public class TelaDetalhesProjeto extends JFrame {
    private ControladorProjeto controladorProjeto;
    private ControladorAtividade controladorAtividade;
    private Projeto projeto;
    private Professor professorLogado;
    
    private JTable tabelaEquipe;
    private DefaultTableModel modeloTabelaEquipe;
    private JTable tabelaAtividades;
    private DefaultTableModel modeloTabelaAtividades;
    
    private JLabel lblTitulo;
    private JLabel lblStatus;
    private JTextArea txtDescricao;
    
    public TelaDetalhesProjeto(ControladorProjeto controladorProjeto,
                              ControladorAtividade controladorAtividade,
                              Projeto projeto,
                              Professor professorLogado) {
        this.controladorProjeto = controladorProjeto;
        this.controladorAtividade = controladorAtividade;
        this.projeto = projeto;
        this.professorLogado = professorLogado;
        
        setTitle("Detalhes do Projeto");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel de cabeçalho com informações do projeto
        JPanel painelCabecalho = criarPainelCabecalho();
        painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
        
        // Painel central com abas
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Equipe", criarPainelEquipe());
        abas.addTab("Atividades", criarPainelAtividades());
        painelPrincipal.add(abas, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = criarPainelBotoes();
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        // Carregar dados iniciais
        atualizarCabecalho();
        carregarEquipe();
        carregarAtividades();
    }
    
    private JPanel criarPainelCabecalho() {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Informações do Projeto"));
        
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelInfo.add(new JLabel("Título:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblTitulo = new JLabel();
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        painelInfo.add(lblTitulo, gbc);
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        painelInfo.add(new JLabel("Status:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblStatus = new JLabel();
        painelInfo.add(lblStatus, gbc);
        
        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        painelInfo.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescricao = new JTextArea(3, 30);
        txtDescricao.setEditable(false);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        painelInfo.add(scrollDesc, gbc);
        
        painel.add(painelInfo, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JPanel criarPainelEquipe() {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Tabela de equipe
        String[] colunas = {"Nome", "Email", "CPF", "Curso"};
        modeloTabelaEquipe = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEquipe = new JTable(modeloTabelaEquipe);
        tabelaEquipe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaEquipe.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(tabelaEquipe);
        painel.add(scrollPane, BorderLayout.CENTER);
        
        // Botões da equipe (UC08)
        JPanel painelBotoesEquipe = new JPanel();
        painelBotoesEquipe.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnAdicionarProfessor = new JButton("Adicionar Professor");
        btnAdicionarProfessor.setFont(new Font("Arial", Font.PLAIN, 12));
        btnAdicionarProfessor.addActionListener(e -> abrirDialogoAdicionarProfessor());
        
        JButton btnAtualizarEquipe = new JButton("Atualizar");
        btnAtualizarEquipe.setFont(new Font("Arial", Font.PLAIN, 12));
        btnAtualizarEquipe.addActionListener(e -> carregarEquipe());
        
        painelBotoesEquipe.add(btnAdicionarProfessor);
        painelBotoesEquipe.add(btnAtualizarEquipe);
        
        painel.add(painelBotoesEquipe, BorderLayout.SOUTH);
        
        return painel;
    }
    
    private JPanel criarPainelAtividades() {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Tabela de atividades
        String[] colunas = {"Descrição", "Carga Horária (h)", "Data"};
        modeloTabelaAtividades = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaAtividades = new JTable(modeloTabelaAtividades);
        tabelaAtividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaAtividades.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(tabelaAtividades);
        painel.add(scrollPane, BorderLayout.CENTER);
        
        // Botões de atividades (UC09 e UC10)
        JPanel painelBotoesAtividades = new JPanel();
        painelBotoesAtividades.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnNovaAtividade = new JButton("Nova Atividade");
        btnNovaAtividade.setFont(new Font("Arial", Font.PLAIN, 12));
        btnNovaAtividade.addActionListener(e -> abrirDialogoNovaAtividade());
        
        JButton btnExcluirAtividade = new JButton("Excluir Atividade");
        btnExcluirAtividade.setFont(new Font("Arial", Font.PLAIN, 12));
        btnExcluirAtividade.addActionListener(e -> excluirAtividadeSelecionada());
        
        JButton btnAtualizarAtividades = new JButton("Atualizar");
        btnAtualizarAtividades.setFont(new Font("Arial", Font.PLAIN, 12));
        btnAtualizarAtividades.addActionListener(e -> carregarAtividades());
        
        painelBotoesAtividades.add(btnNovaAtividade);
        painelBotoesAtividades.add(btnExcluirAtividade);
        painelBotoesAtividades.add(btnAtualizarAtividades);
        
        painel.add(painelBotoesAtividades, BorderLayout.SOUTH);
        
        return painel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel();
        painel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        // UC11 - Excluir Projeto
        JButton btnExcluirProjeto = new JButton("Excluir Projeto");
        btnExcluirProjeto.setFont(new Font("Arial", Font.BOLD, 14));
        btnExcluirProjeto.setForeground(Color.RED);
        btnExcluirProjeto.addActionListener(e -> excluirProjeto());
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFechar.addActionListener(e -> dispose());
        
        painel.add(btnExcluirProjeto);
        painel.add(btnFechar);
        
        return painel;
    }
    
    private void atualizarCabecalho() {
        lblTitulo.setText(projeto.getTitulo());
        lblStatus.setText(projeto.getStatus());
        txtDescricao.setText(projeto.getDescricao());
    }
    
    private void carregarEquipe() {
        modeloTabelaEquipe.setRowCount(0);
        List<Professor> equipe = projeto.getEquipe();
        
        for (Professor prof : equipe) {
            Object[] linha = {
                prof.getNome(),
                prof.getEmail(),
                prof.getCpf(),
                prof.getCurso()
            };
            modeloTabelaEquipe.addRow(linha);
        }
    }
    
    private void carregarAtividades() {
        modeloTabelaAtividades.setRowCount(0);
        List<Atividade> atividades = projeto.getAtividades();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Atividade ativ : atividades) {
            Object[] linha = {
                ativ.getDescricao(),
                ativ.getCargaHoraria(),
                sdf.format(ativ.getData())
            };
            modeloTabelaAtividades.addRow(linha);
        }
    }
    
    /**
     * UC08 - Atribuir Professor à Equipe
     */
    private void abrirDialogoAdicionarProfessor() {
        CatalogoProfessor catalogo = controladorProjeto.getCatalogoProfessor();
        List<Professor> todosProfessores = catalogo.getTodosProfessores();
        
        if (todosProfessores.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Não há professores cadastrados no sistema.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Criar lista de professores disponíveis (que não estão na equipe)
        java.util.List<Professor> disponiveis = new java.util.ArrayList<>();
        for (Professor p : todosProfessores) {
            if (!projeto.isMembro(p)) {
                disponiveis.add(p);
            }
        }
        
        if (disponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos os professores já fazem parte da equipe.",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Criar array de nomes para JComboBox
        String[] nomesProfessores = new String[disponiveis.size()];
        for (int i = 0; i < disponiveis.size(); i++) {
            nomesProfessores[i] = disponiveis.get(i).getNome() + " (" + disponiveis.get(i).getEmail() + ")";
        }
        
        String selecionado = (String) JOptionPane.showInputDialog(
            this,
            "Selecione o professor para adicionar à equipe:",
            "Adicionar Professor",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nomesProfessores,
            nomesProfessores[0]
        );
        
        if (selecionado != null) {
            int indice = java.util.Arrays.asList(nomesProfessores).indexOf(selecionado);
            Professor professorSelecionado = disponiveis.get(indice);
            
            String resultado = controladorProjeto.atribuirProfessor(projeto, professorSelecionado);
            
            if (resultado.equals("Professor adicionado à equipe com sucesso!")) {
                JOptionPane.showMessageDialog(this,
                    resultado,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
                carregarEquipe();
            } else {
                JOptionPane.showMessageDialog(this,
                    resultado,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * UC09 - Adicionar Atividade
     */
    private void abrirDialogoNovaAtividade() {
        JDialog dialogo = new JDialog(this, "Nova Atividade", true);
        dialogo.setSize(500, 300);
        dialogo.setLocationRelativeTo(this);
        
        JPanel painelForm = new JPanel();
        painelForm.setLayout(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelForm.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField txtDescAtiv = new JTextField(30);
        painelForm.add(txtDescAtiv, gbc);
        
        // Carga Horária
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        painelForm.add(new JLabel("Carga Horária (h):"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JSpinner spinnerCarga = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        painelForm.add(spinnerCarga, gbc);
        
        // Data
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        painelForm.add(new JLabel("Data:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JSpinner dateChooser = new JSpinner(new SpinnerDateModel()) {
            private static final long serialVersionUID = 1L;
            public Date getDate() { return (Date) getValue(); }
            public void setDate(Date d) { setValue(d); }
            public void setDateFormatString(String s) {
            JSpinner.DateEditor ed = new JSpinner.DateEditor(this, s);
            this.setEditor(ed);
            }
        };
        // valor inicial usando Calendar (import já presente no arquivo)
        dateChooser.setValue(Calendar.getInstance().getTime());
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(new Date());
        painelForm.add(dateChooser, gbc);
        
        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            String descricao = txtDescAtiv.getText().trim();
            int cargaHoraria = (Integer) spinnerCarga.getValue();
            Date data = dateChooser.getDate();
            
            String resultado = controladorAtividade.adicionarAtividade(projeto, descricao, cargaHoraria, data);
            
            if (resultado.equals("Atividade adicionada com sucesso!")) {
                JOptionPane.showMessageDialog(dialogo,
                    resultado,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
                carregarAtividades();
                dialogo.dispose();
            } else {
                JOptionPane.showMessageDialog(dialogo,
                    resultado,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        
        dialogo.setLayout(new BorderLayout());
        dialogo.add(painelForm, BorderLayout.CENTER);
        dialogo.add(painelBotoes, BorderLayout.SOUTH);
        
        dialogo.setVisible(true);
    }
    
    /**
     * UC10 - Excluir Atividade
     */
    private void excluirAtividadeSelecionada() {
        int linhaSelecionada = tabelaAtividades.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione uma atividade para excluir.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir esta atividade?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            List<Atividade> atividades = projeto.getAtividades();
            Atividade atividadeSelecionada = atividades.get(linhaSelecionada);
            
            String resultado = controladorAtividade.excluirAtividade(projeto, atividadeSelecionada);
            
            if (resultado.equals("Atividade excluída com sucesso!")) {
                JOptionPane.showMessageDialog(this,
                    resultado,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
                carregarAtividades();
            } else {
                JOptionPane.showMessageDialog(this,
                    resultado,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * UC11 - Excluir Projeto
     */
    private void excluirProjeto() {
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "ATENÇÃO: Esta ação é irreversível!\n\n" +
            "Tem certeza que deseja excluir o projeto '" + projeto.getTitulo() + "'?\n" +
            "Todas as atividades e dados da equipe serão perdidos.",
            "Confirmar Exclusão do Projeto",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            String resultado = controladorProjeto.excluirProjeto(projeto);
            
            if (resultado.equals("Projeto excluído com sucesso!")) {
                JOptionPane.showMessageDialog(this,
                    resultado,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    resultado,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
