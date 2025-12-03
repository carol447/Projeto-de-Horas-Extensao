package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.Candidatura;
import model.PAEG;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TelaMeusPAEGs extends JFrame {

    public TelaMeusPAEGs(Aluno aluno) {

        setTitle("Meus PAEGs");
        setSize(700, 450); 
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. LISTA DE PAEGS (CENTER)
        // ===================================
        List<Candidatura> lista = Sistema.controladorCandidatura.visualizarMinhasCandidaturas(aluno);

        DefaultListModel<String> model = new DefaultListModel<>();

        if (lista.isEmpty()) {
            model.addElement("Você ainda não se inscreveu em nenhum PAEG.");
        } else {
            for (Candidatura c : lista) {
                PAEG paeg = c.getPaeg();
                String linha = String.format(
                    "%-20s | Atividade: %-25s | Projeto: %-25s | Status: %s",
                    paeg.getNome(),
                    paeg.getAtividade().getNome(),
                    paeg.getAtividade().getProjeto().getNome(),
                    c.getStatus()
                );
                model.addElement(linha);
            }
        }

        JList<String> jlist = new JList<>(model);
        jlist.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        
        JScrollPane scrollPane = new JScrollPane(jlist);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Projetos nos quais você se candidatou"));
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
                util.EncerrarSistema.encerrarAplicacao(TelaMeusPAEGs.this);
            }
        }
        );
    }
}
