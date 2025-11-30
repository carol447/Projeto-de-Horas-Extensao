package view;

import com.mycompany.horasextensao.Sistema;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarProjetos extends JFrame {
    
    private Professor professor;

    public TelaListarProjetos(Professor prof) {
        
        this.professor = prof;

        setTitle("Meus Projetos");
        setSize(500, 400);
        setLocationRelativeTo(null);

        List<Projeto> projetos = Sistema.catalogoProjeto.buscarPorMembro(this.professor);

        DefaultListModel<Projeto> model = new DefaultListModel<>();
        projetos.forEach(model::addElement);

        JList<Projeto> lista = new JList<>(model);
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
            Projeto p = lista.getSelectedValue();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Selecione um projeto.");
                return;
            }
            dispose();
            new TelaProjetoDetalhes(prof, p).setVisible(true);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeProfessor(prof).setVisible(true);
        });
    }
}
