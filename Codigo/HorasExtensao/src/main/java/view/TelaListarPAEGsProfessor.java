package view;

import com.mycompany.horasextensao.Sistema;
import model.Atividade;
import model.PAEG;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaListarPAEGsProfessor extends JFrame {

    public TelaListarPAEGsProfessor(Professor prof, Atividade atividade) {

        setTitle("PAEGs da Atividade: " + atividade.getNome()); 
        setSize(550, 400); // Aumentei o tamanho para um visual melhor
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
        List<PAEG> listaPaeg = Sistema.catalogoPAEG.getPAEGsDaAtividade(atividade);

        DefaultListModel<PAEG> model = new DefaultListModel<>();
        listaPaeg.forEach(model::addElement);

        JList<PAEG> lista = new JList<>(model);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fonte para visualização de dados

        // Melhoria visual: Borda com título
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setBorder(BorderFactory.createTitledBorder("PAEGs cadastrados para esta atividade"));

        root.add(scrollPane, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JButton btnAbrir = new JButton("Abrir PAEG");
        JButton btnVoltar = new JButton("Voltar");

        // Painel para centralizar os botões
        // FlowLayout.CENTER com espaçamento de 20px
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        
        Dimension buttonSize = new Dimension(140, 30);
        btnAbrir.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);
        
        botoes.add(btnAbrir);
        botoes.add(btnVoltar);
        
        root.add(botoes, BorderLayout.SOUTH);

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
        
                                       setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaListarPAEGsProfessor.this);
    }
}
);
    }
}
