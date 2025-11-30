package view;

import model.Professor;
import model.Projeto;
import javax.swing.*;
import java.awt.*;

public class TelaProjetoDetalhes extends JFrame {

    public TelaProjetoDetalhes(Professor prof, Projeto projeto) {

        setTitle("Projeto: " + projeto.getNome());
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel lblTitulo = new JLabel("Projeto: " + projeto.getNome(), SwingConstants.CENTER);

        JButton btnAtividades = new JButton("Atividades");
        JButton btnAdicionarAtividade = new JButton("Adicionar Atividade");
        JButton btnGerenciarProfessores = new JButton("Gerenciar Professores");
        JButton btnVoltar = new JButton("Voltar");

        add(lblTitulo);
        add(btnAtividades);
        add(btnAdicionarAtividade);
        add(btnGerenciarProfessores);
        add(btnVoltar);

        // ABRIR LISTA DE ATIVIDADES
        btnAtividades.addActionListener(e -> {
            dispose();
            new TelaListarAtividades(prof, projeto).setVisible(true);
        });

        // ADICIONAR ATIVIDADE
        btnAdicionarAtividade.addActionListener(e -> {
            dispose();
            new TelaCadastrarAtividade(prof, projeto).setVisible(true);
        });

        // GERENCIAR PROFESSORES  (CASO DE USO 7)
        btnGerenciarProfessores.addActionListener(e -> {
            dispose();
            new TelaGerenciarProfessores(prof, projeto).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaListarProjetos(prof).setVisible(true);
        });
    }
}
