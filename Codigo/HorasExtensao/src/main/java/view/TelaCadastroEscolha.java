package view;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroEscolha extends JFrame {

    public TelaCadastroEscolha() {
        setTitle("Escolha o Tipo de Cadastro");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,1,10,10));

        JButton btnAluno = new JButton("Cadastrar Aluno");
        JButton btnProfessor = new JButton("Cadastrar Professor");

        add(btnAluno);
        add(btnProfessor);

        btnAluno.addActionListener(e -> {
            dispose();
            new TelaCadastroAluno().setVisible(true);
        });

        btnProfessor.addActionListener(e -> {
            dispose();
            new TelaCadastroProfessor().setVisible(true);
        });
        
                                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaCadastroEscolha.this);
    }
}
);
    }
}
