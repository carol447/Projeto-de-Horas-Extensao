package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.Candidatura;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaMinhasCandidaturas extends JFrame {

    public TelaMinhasCandidaturas(Aluno aluno) {

        setTitle("Minhas Candidaturas");
        setSize(500, 350);
        setLocationRelativeTo(null);

        List<Candidatura> lista = Sistema.controladorCandidatura.visualizarMinhasCandidaturas(aluno);

        DefaultListModel<Candidatura> model = new DefaultListModel<>();
        lista.forEach(model::addElement);

        JList<Candidatura> listaCandidaturas = new JList<>(model);

        JButton btnVoltar = new JButton("Voltar");

        add(new JScrollPane(listaCandidaturas), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.add(btnVoltar);
        add(botoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeAluno(aluno).setVisible(true);
        });
    }
}
