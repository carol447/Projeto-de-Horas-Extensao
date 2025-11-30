package view;

import com.mycompany.horasextensao.Sistema;
import model.Atividade;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarAtividades extends JFrame {

    public TelaListarAtividades(Professor prof, Projeto projeto) {

        setTitle("Atividades do Projeto");
        setSize(500, 350);
        setLocationRelativeTo(null);

        List<Atividade> atividades = Sistema.catalogoAtividade.getAtividadesDoProjeto(projeto);

        DefaultListModel<Atividade> model = new DefaultListModel<>();
        atividades.forEach(model::addElement);

        JList<Atividade> lista = new JList<>(model);

        JButton btnAbrir = new JButton("Abrir Atividade");
        JButton btnVoltar = new JButton("Voltar");

        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.add(btnAbrir);
        botoes.add(btnVoltar);
        add(botoes, BorderLayout.SOUTH);

        btnAbrir.addActionListener(e -> {
            Atividade at = lista.getSelectedValue();
            if (at == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma atividade.");
                return;
            }
            dispose();
            new TelaAtividadeDetalhes(prof, projeto, at).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaProjetoDetalhes(prof, projeto).setVisible(true);
        });
    }
}
