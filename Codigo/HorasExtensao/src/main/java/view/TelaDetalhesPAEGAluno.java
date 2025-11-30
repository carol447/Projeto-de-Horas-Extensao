package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.PAEG;

import javax.swing.*;
import java.awt.*;

public class TelaDetalhesPAEGAluno extends JFrame {

    public TelaDetalhesPAEGAluno(Aluno aluno, PAEG paeg) {

        setTitle("Detalhes do PAEG");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea txt = new JTextArea();
        txt.setEditable(false);
        txt.setText(
                "PAEG: " + paeg.getNome() + "\n" +
                "Carga Horária: " + paeg.getCargaHoraria() + "\n" +
                "Máximo de alunos: " + paeg.getMaximoCandidaturas() + "\n" +
                "Data início inscrições: " + paeg.getDataInicialCandidatura() + "\n" +
                "Data fim inscrições: " + paeg.getDataFinalCandidatura() + "\n" +
                "Projeto: " + paeg.getAtividade().getProjeto().getNome() + "\n" +
                "Atividade: " + paeg.getAtividade().getNome()
        );

        JButton btnCandidatar = new JButton("Candidatar-se");
        JButton btnVoltar = new JButton("Voltar");

        JPanel botoes = new JPanel();
        botoes.add(btnCandidatar);
        botoes.add(btnVoltar);

        add(new JScrollPane(txt), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnCandidatar.addActionListener(e -> {
            boolean ok = Sistema.controladorCandidatura.candidatar(paeg, aluno);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Candidatura realizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Não foi possível realizar a candidatura.\n" +
                                "Motivos possíveis:\n" +
                                "- Já existe uma candidatura sua\n" +
                                "- Período de inscrição fechado\n" +
                                "- Número máximo de candidatos atingido",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaListarPAEGsDisponiveis(aluno).setVisible(true);
        });
    }
}
