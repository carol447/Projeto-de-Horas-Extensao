package view;

import com.mycompany.horasextensao.Sistema;
import model.Atividade;
import model.Professor;
import model.Projeto;

import javax.swing.*;
import java.awt.*;

public class TelaAtividadeDetalhes extends JFrame {

    public TelaAtividadeDetalhes(Professor prof, Projeto projeto, Atividade atividade) {

        setTitle("Atividade: " + atividade.getNome());
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.append("Nome: " + atividade.getNome() + "\n");
        info.append("Descrição: " + atividade.getDescricao() + "\n");
        info.append("PAEGs cadastrados: " + atividade.getPaegs().size() + "\n");

        add(new JScrollPane(info), BorderLayout.CENTER);

        JButton btnPAEGs = new JButton("PAEGs");
        JButton btnAdicionarPAEG = new JButton("Adicionar PAEG");
        JButton btnExcluir = new JButton("Excluir Atividade");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painel = new JPanel();
        painel.add(btnPAEGs);
        painel.add(btnAdicionarPAEG);
        painel.add(btnExcluir);
        painel.add(btnVoltar);

        add(painel, BorderLayout.SOUTH);

        btnPAEGs.addActionListener(e -> {
            dispose();
            new TelaListarPAEGsProfessor(prof, atividade).setVisible(true);
        });

        btnAdicionarPAEG.addActionListener(e -> {
            dispose();
            new TelaCadastrarPAEG(prof, atividade).setVisible(true);
        });

        btnExcluir.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this, "Excluir atividade?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                if (Sistema.controladorAtividade.excluirAtividade(atividade, prof)) {
                    JOptionPane.showMessageDialog(this, "Atividade excluída!");
                    new TelaListarAtividades(prof, projeto).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Há PAEGs vinculados. Não é possível excluir.");
                }
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaProjetoDetalhes(prof, projeto).setVisible(true);
        });
    }
}
