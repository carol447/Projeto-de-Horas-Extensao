package view;

import model.Professor;
import javax.swing.*;
import java.awt.*;

public class TelaHomeProfessor extends JFrame {
    
    private Professor professor;

    public TelaHomeProfessor(Professor professor) {
        
        this.professor = professor;

        setTitle("Área do Professor");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnNovoProjeto = new JButton("Adicionar Novo Projeto");
        JButton btnProjetos = new JButton("Projetos");
        JButton btnSair = new JButton("Sair");

        add(btnNovoProjeto);
        add(btnProjetos);
        add(btnSair);

        btnNovoProjeto.addActionListener(e -> {
            dispose();
            new TelaCadastrarProjeto(this.professor).setVisible(true);
        });

        btnProjetos.addActionListener(e -> {
            dispose();
            new TelaListarProjetos(this.professor).setVisible(true);
        });

        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
    }
}
