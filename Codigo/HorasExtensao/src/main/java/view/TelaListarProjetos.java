package view;

import com.mycompany.horasextensao.Sistema;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarProjetos extends JFrame {

    public TelaListarProjetos(Professor prof) {

        setTitle("Meus Projetos");
        setSize(500, 400);
        setLocationRelativeTo(null);

        List<Projeto> projetos = Sistema.catalogoProjeto.buscarPorMembro(prof);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Projeto p : projetos) {
            model.addElement(
                p.getNome() + " | Curso: " + p.getCurso() +
                " | Professores: " + p.getProfessores().size()
            );
        }

        JList<String> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnAbrir = new JButton("Abrir Projeto");
        JButton btnVoltar = new JButton("Voltar");

        setLayout(new BorderLayout());
        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.add(btnAbrir);
        botoes.add(btnVoltar);
        add(botoes, BorderLayout.SOUTH);

        btnAbrir.addActionListener(e -> {
            int index = lista.getSelectedIndex();
            if (index < 0) return;

            Projeto p = projetos.get(index);
            dispose();
            new TelaProjetoDetalhes(prof, p).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeProfessor(prof).setVisible(true);
        });
    }
}

