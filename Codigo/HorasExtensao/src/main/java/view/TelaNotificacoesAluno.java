package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.Notificacao;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaNotificacoesAluno extends JFrame {

    public TelaNotificacoesAluno(Aluno aluno) {

        setTitle("Notificações");
        setSize(550, 400); // Aumentei a largura
        setLocationRelativeTo(null);
        // setLayout(new BorderLayout()); <— Será usado no root

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. LISTA DE NOTIFICAÇÕES (CENTER)
        // ===================================
        List<Notificacao> lista = Sistema.catalogoNotificacao.buscarPorAluno(aluno);

        DefaultListModel<String> model = new DefaultListModel<>();

        if (lista.isEmpty()) {
            model.addElement("Nenhuma notificação encontrada.");
        } else {
            for (Notificacao n : lista) {
                model.addElement("• " + n.getMensagem());
            }
        }

        JList<String> jlist = new JList<>(model);
        jlist.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Adiciona borda com título
        JScrollPane scrollPane = new JScrollPane(jlist);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Suas Notificações"));
        root.add(scrollPane, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        Dimension buttonSize = new Dimension(100, 30);
        btnVoltar.setPreferredSize(buttonSize);
        
        painelBotoes.add(btnVoltar);
        
        root.add(painelBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeAluno(aluno).setVisible(true);
        });

        add(btnVoltar, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaNotificacoesAluno.this);
            }
        }
        );
    }
}
