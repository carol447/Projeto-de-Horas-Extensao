package view;

import model.Professor;
import model.Projeto;
import javax.swing.*;
import java.awt.*;

public class TelaProjetoDetalhes extends JFrame {

    public TelaProjetoDetalhes(Professor prof, Projeto projeto) {

        setTitle("Projeto: " + projeto.getNome());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.append("Nome do Projeto: " + projeto.getNome() + "\n");
        info.append("Curso: " + projeto.getCurso() + "\n");
        info.append("Descrição: " + projeto.getDescricao() + "\n\n");

        info.append("Professores envolvidos:\n");
        for (Professor p : projeto.getProfessores()) {
            info.append(" - " + p.getNome() + " (" + p.getEmail() + ")\n");
        }

        add(new JScrollPane(info), BorderLayout.CENTER);

        JButton btnAtividades = new JButton("Atividades");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painel = new JPanel();
        painel.add(btnAtividades);
        painel.add(btnVoltar);

        add(painel, BorderLayout.SOUTH);

        btnAtividades.addActionListener(e -> {
            dispose();
            new TelaListarAtividades(prof, projeto).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaListarProjetos(prof).setVisible(true);
        });
    }
}
