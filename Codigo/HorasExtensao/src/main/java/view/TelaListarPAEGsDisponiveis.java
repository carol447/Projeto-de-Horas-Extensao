package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.PAEG;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarPAEGsDisponiveis extends JFrame {

    public TelaListarPAEGsDisponiveis(Aluno aluno) {

        setTitle("PAEGs Disponíveis");
        setSize(500, 350);
        setLocationRelativeTo(null);

        List<PAEG> lista = Sistema.controladorPAEG.consultarPAEGsDisponiveis(aluno);

        DefaultListModel<PAEG> model = new DefaultListModel<>();
        lista.forEach(model::addElement);

        JList<PAEG> listaPAEG = new JList<>(model);

        JButton btnAbrir = new JButton("Ver Detalhes");
        JButton btnVoltar = new JButton("Voltar");

        add(new JScrollPane(listaPAEG), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.add(btnAbrir);
        botoes.add(btnVoltar);
        add(botoes, BorderLayout.SOUTH);

        btnAbrir.addActionListener(e -> {
            PAEG paeg = listaPAEG.getSelectedValue();
            if (paeg == null) {
                JOptionPane.showMessageDialog(this, "Selecione um PAEG.");
                return;
            }
            dispose();
            new TelaDetalhesPAEGAluno(aluno, paeg).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeAluno(aluno).setVisible(true);
        });
    }
}
