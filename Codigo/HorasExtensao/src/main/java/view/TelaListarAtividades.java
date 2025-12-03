package view;

import com.mycompany.horasextensao.Sistema;
import model.Atividade;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaListarAtividades extends JFrame {

    public TelaListarAtividades(Professor prof, Projeto projeto) {

        setTitle("Atividades do Projeto: " + projeto.getNome()); 
        setSize(550, 450); 
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15)); 
        add(root);

        // ===================================
        // 2. LISTA (CENTER)
        // ===================================
        List<Atividade> atividades = Sistema.catalogoAtividade.getAtividadesDoProjeto(projeto);

        DefaultListModel<Atividade> model = new DefaultListModel<>();
        atividades.forEach(model::addElement);

        JList<Atividade> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Atividades Registradas"));

        root.add(scrollPane, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JButton btnAbrir = new JButton("Abrir Atividade");
        JButton btnVoltar = new JButton("Voltar");

        // Painel para centralizar os botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Dimension buttonSize = new Dimension(140, 30);
        btnAbrir.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        botoes.add(btnAbrir);
        botoes.add(btnVoltar);

        root.add(botoes, BorderLayout.SOUTH);

        btnAbrir.addActionListener(e -> {
            Atividade at = lista.getSelectedValue();
            if (at == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma atividade.");
                return;
            }
            dispose();
            new TelaAtividadeDetalhes(prof, projeto, at).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaProjetoDetalhes(prof, projeto).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaListarAtividades.this);
            }
        }
        );
    }
}
