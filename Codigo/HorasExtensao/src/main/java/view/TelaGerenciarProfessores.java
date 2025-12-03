package view;

import com.mycompany.horasextensao.Sistema;
import model.Professor;
import model.Projeto;
import catalog.CatalogoProfessor;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TelaGerenciarProfessores extends JFrame {

    public TelaGerenciarProfessores(Professor profLogado, Projeto projeto) {

        setTitle("Professores do Projeto");
        setSize(550, 450);
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. PAINEL DE BUSCA (NORTH)
        // ===================================
        JPanel painelBusca = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtBusca = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");

        // Rótulo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        painelBusca.add(new JLabel("Buscar Professor (nome/email):"), gbc);

        // Campo de busca
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        painelBusca.add(txtBusca, gbc);

        // Botão de busca
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        painelBusca.add(btnBuscar, gbc);

        root.add(painelBusca, BorderLayout.NORTH);

        // ===================================
        // 3. LISTA DE PROFESSORES (CENTER)
        // ===================================
        DefaultListModel<Professor> model = new DefaultListModel<>();
        projeto.getProfessores().forEach(model::addElement);

        JList<Professor> listaProfessores = new JList<>(model);
        JScrollPane scrollLista = new JScrollPane(listaProfessores);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Membros do Projeto e Resultados da Busca"));

        root.add(scrollLista, BorderLayout.CENTER);

        // ===================================
        // 4. BOTÕES DE AÇÃO (SOUTH)
        // ===================================
        JButton btnAdicionar = new JButton("Adicionar Selecionado");
        JButton btnVoltar = new JButton("Voltar");

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Dimension buttonSize = new Dimension(180, 30);
        btnAdicionar.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(new Dimension(100, 30));

        rodape.add(btnAdicionar);
        rodape.add(btnVoltar);

        root.add(rodape, BorderLayout.SOUTH);

        // ================= BUSCAR PROFESSOR =================
        btnBuscar.addActionListener(e -> {
            String termo = txtBusca.getText().trim();
            CatalogoProfessor cp = Sistema.catalogoProfessor;

            model.clear();

            // Buscar por email exato
            Professor pEmail = cp.encontrarProfPorEmail(termo);
            if (pEmail != null) {
                model.addElement(pEmail);
                return;
            }

            // Buscar por nome parcial
            for (Professor p : cp.getProfessores()) {
                if (p.getNome().toLowerCase().contains(termo.toLowerCase())) {
                    model.addElement(p);
                }
            }
        });

        // ================ ADICIONAR PROFESSOR AO PROJETO ===============
        btnAdicionar.addActionListener(e -> {
            Professor selecionado = listaProfessores.getSelectedValue();

            if (selecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um professor.");
                return;
            }

            if (projeto.isMembro(selecionado)) {
                JOptionPane.showMessageDialog(this, "Este professor já faz parte do projeto.");
                return;
            }

            projeto.adicionarProfessor(selecionado);
            Sistema.salvarDados();

            JOptionPane.showMessageDialog(this, "Professor adicionado com sucesso!");
        });

        // VOLTAR
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaProjetoDetalhes(profLogado, projeto).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaGerenciarProfessores.this);
            }
        }
        );
    }
}
