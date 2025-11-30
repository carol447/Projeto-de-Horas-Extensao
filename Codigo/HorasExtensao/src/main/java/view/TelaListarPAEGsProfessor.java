package view;

import com.mycompany.horasextensao.Sistema;
import model.Atividade;
import model.PAEG;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarPAEGsProfessor extends JFrame {

    public TelaListarPAEGsProfessor(Professor prof, Atividade atividade) {

        setTitle("PAEGs da Atividade");
        setSize(500, 350);
        setLocationRelativeTo(null);

        List<PAEG> listaPaeg = Sistema.catalogoPAEG.getPAEGsDaAtividade(atividade);

        DefaultListModel<PAEG> model = new DefaultListModel<>();
        listaPaeg.forEach(model::addElement);

        JList<PAEG> lista = new JList<>(model);

        JButton btnAbrir = new JButton("Abrir PAEG");
        JButton btnVoltar = new JButton("Voltar");

        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.add(btnAbrir);
        botoes.add(btnVoltar);
        add(botoes, BorderLayout.SOUTH);

        btnAbrir.addActionListener(e -> {
            PAEG p = lista.getSelectedValue();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Selecione um PAEG.");
                return;
            }
            dispose();
            new TelaPAEGDetalhes(prof, atividade, p).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaAtividadeDetalhes(prof, atividade.getProjeto(), atividade).setVisible(true);
        });
    }
}
