package view;

import com.mycompany.horasextensao.Sistema;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaListarProjetos extends JFrame {

    public TelaListarProjetos(Professor prof) {

        setTitle("Meus Projetos");
        setSize(550, 450); // Aumentei um pouco o tamanho
        setLocationRelativeTo(null);

        // 1. Painel principal para margens externas
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margens externas
        add(root);

        List<Projeto> projetos = Sistema.catalogoProjeto.buscarPorMembro(prof);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Projeto p : projetos) {
            model.addElement(
                    p.getNome() + " | Curso: " + p.getCurso()
                    + " | Professores: " + p.getProfessores().size()
            );
        }

        JList<String> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 2. Melhorar a aparência da lista (Opcional: Adicionar uma borda com título)
        lista.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fonte monoespaçada ajuda na leitura
        lista.setBorder(BorderFactory.createTitledBorder("Projetos do Professor(a) " + prof.getNome()));

        JScrollPane scrollPane = new JScrollPane(lista);
        root.add(scrollPane, BorderLayout.CENTER);

        // 3. Painel dos botões (Rodapé)
        // Usa FlowLayout.CENTER para centralizar os botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5)); // Espaçamento horizontal e vertical

        JButton btnAbrir = new JButton("Abrir Projeto");
        JButton btnVoltar = new JButton("Voltar");

        // Dá um tamanho fixo aos botões para parecerem mais uniformes (opcional)
        Dimension buttonSize = new Dimension(120, 30);
        btnAbrir.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        botoes.add(btnAbrir);
        botoes.add(btnVoltar);

        root.add(botoes, BorderLayout.SOUTH);

        btnAbrir.addActionListener(e -> {
            int index = lista.getSelectedIndex();
            if (index < 0) {
                return;
            }

            Projeto p = projetos.get(index);
            dispose();
            new TelaProjetoDetalhes(prof, p).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeProfessor(prof).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaListarProjetos.this);
            }
        }
        );
    }
}
