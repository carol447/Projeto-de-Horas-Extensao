package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.PAEG;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaListarPAEGsDisponiveis extends JFrame {

    public TelaListarPAEGsDisponiveis(Aluno aluno) {

        setTitle("PAEGs Disponíveis");
        setSize(550, 400); 
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15)); // Margens externas
        add(root);

        // ===================================
        // 2. LISTA DE PAEGS (CENTER)
        // ===================================
        List<PAEG> lista = Sistema.controladorPAEG.consultarPAEGsDisponiveis(aluno);

        DefaultListModel<PAEG> model = new DefaultListModel<>();
        lista.forEach(model::addElement);

        JList<PAEG> listaPAEG = new JList<>(model);
        listaPAEG.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Melhoria visual: Adiciona borda com título
        JScrollPane scrollPane = new JScrollPane(listaPAEG);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Vagas de Extensão Abertas para " + aluno.getCurso()));

        root.add(scrollPane, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JButton btnAbrir = new JButton("Ver Detalhes");
        JButton btnVoltar = new JButton("Voltar");

        // Painel para centralizar os botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Dimension buttonSize = new Dimension(140, 30);
        btnAbrir.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        botoes.add(btnAbrir);
        botoes.add(btnVoltar);

        root.add(botoes, BorderLayout.SOUTH);

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

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Não há vagas de extensão abertas para seu curso no momento.");
        }

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaListarPAEGsDisponiveis.this);
            }
        }
        );
    }
}
