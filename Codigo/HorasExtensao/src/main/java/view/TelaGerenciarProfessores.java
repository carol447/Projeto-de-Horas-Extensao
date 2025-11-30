package view;

import com.mycompany.horasextensao.Sistema;
import model.Professor;
import model.Projeto;
import catalog.CatalogoProfessor;

import javax.swing.*;
import java.awt.*;

public class TelaGerenciarProfessores extends JFrame {

    public TelaGerenciarProfessores(Professor profLogado, Projeto projeto) {

        setTitle("Professores do Projeto");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Lista dos professores do projeto
        DefaultListModel<Professor> model = new DefaultListModel<>();
        projeto.getProfessores().forEach(model::addElement);

        JList<Professor> listaProfessores = new JList<>(model);

        // Campo de busca
        JTextField txtBusca = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAdicionar = new JButton("Adicionar Selecionado");
        JButton btnVoltar = new JButton("Voltar");

        JPanel topo = new JPanel(new GridLayout(1, 3));
        topo.add(new JLabel("Buscar Professor (nome/email):"));
        topo.add(txtBusca);
        topo.add(btnBuscar);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(listaProfessores), BorderLayout.CENTER);

        JPanel rodape = new JPanel();
        rodape.add(btnAdicionar);
        rodape.add(btnVoltar);
        add(rodape, BorderLayout.SOUTH);

        // ================= BUSCAR PROFESSOR =================
        btnBuscar.addActionListener(e -> {
            String termo = txtBusca.getText().trim();
            CatalogoProfessor cp = Sistema.catalogoProfessor;

            model.clear();

            // Buscar por email exato
            Professor pEmail = cp.encontrarProfPorEmail(termo);
            if (pEmail != null) {
                model.addElement(pEmail);
                return;
            }

            // Buscar por nome parcial
            for (Professor p : cp.getProfessores()) {
                if (p.getNome().toLowerCase().contains(termo.toLowerCase())) {
                    model.addElement(p);
                }
            }
        });

        // ================ ADICIONAR PROFESSOR AO PROJETO ===============
        btnAdicionar.addActionListener(e -> {
            Professor selecionado = listaProfessores.getSelectedValue();

            if (selecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um professor.");
                return;
            }

            if (projeto.isMembro(selecionado)) {
                JOptionPane.showMessageDialog(this, "Este professor já faz parte do projeto.");
                return;
            }

            projeto.adicionarProfessor(selecionado);
            Sistema.salvarDados();

            JOptionPane.showMessageDialog(this, "Professor adicionado com sucesso!");
        });

        // VOLTAR
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaProjetoDetalhes(profLogado, projeto).setVisible(true);
        });
    }
}
