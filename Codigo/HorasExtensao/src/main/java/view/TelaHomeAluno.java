package view;

import model.Aluno;

import javax.swing.*;

public class TelaHomeAluno extends JFrame {

    public TelaHomeAluno(Aluno aluno) {
        setTitle("Home do Aluno");
        setSize(400, 300);
        setLocationRelativeTo(null);

        add(new JLabel("Bem-vindo, " + aluno.getNome(), SwingConstants.CENTER));
    }
}
