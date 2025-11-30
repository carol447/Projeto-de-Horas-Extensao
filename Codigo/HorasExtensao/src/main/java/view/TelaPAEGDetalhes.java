package view;

import com.mycompany.horasextensao.Sistema;
import model.Atividade;
import model.Candidatura;
import model.PAEG;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaPAEGDetalhes extends JFrame {

    public TelaPAEGDetalhes(Professor prof, Atividade atividade, PAEG paeg) {

        setTitle("Detalhes do PAEG: " + paeg.getNome());
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Buscar candidaturas
        List<Candidatura> todas = Sistema.catalogoCandidatura.getCandidaturas();
        long pendentes = todas.stream()
                .filter(c -> c.getPaeg().equals(paeg) && c.isPendente())
                .count();

        long aprovados = todas.stream()
                .filter(c -> c.getPaeg().equals(paeg) && c.isAprovada())
                .count();

        // Painel de informações
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.append("Nome: " + paeg.getNome() + "\n");
        info.append("Carga Horária: " + paeg.getCargaHoraria() + "h\n");
        info.append("Máximo de vagas (aprovações): " + paeg.getMaximoCandidaturas() + "\n\n");

        info.append("Período de Candidatura:\n");
        info.append(" - Início: " + sdf.format(paeg.getDataInicialCandidatura()) + "\n");
        info.append(" - Fim: " + sdf.format(paeg.getDataFinalCandidatura()) + "\n\n");

        info.append("Execução:\n");
        info.append(" - Início: " + sdf.format(paeg.getDataInicialExecucao()) + "\n");
        info.append(" - Fim: " + sdf.format(paeg.getDataFinalExecucao()) + "\n\n");

        info.append("Candidaturas:\n");
        info.append(" - Pendentes: " + pendentes + "\n");
        info.append(" - Aprovadas: " + aprovados + "\n\n");

        add(new JScrollPane(info), BorderLayout.CENTER);

        // BOTÕES
        JButton btnCandidaturas = new JButton("Avaliar Candidaturas");
        JButton btnRegistrar = new JButton("Registrar Participação");
        JButton btnExcluir = new JButton("Excluir PAEG");
        JButton btnVoltar = new JButton("Voltar");

        JPanel botoes = new JPanel();
        botoes.add(btnCandidaturas);
        botoes.add(btnRegistrar);
        botoes.add(btnExcluir);
        botoes.add(btnVoltar);

        add(botoes, BorderLayout.SOUTH);

        // ============================================================
        // ABRIR LISTA DE CANDIDATURAS (UC11)
        // ============================================================
        btnCandidaturas.addActionListener(e -> {
            dispose();
            new TelaListarCandidaturasPAEG(prof, paeg).setVisible(true);
        });

        // ============================================================
        // REGISTRAR PARTICIPAÇÃO (UC14)
        // ============================================================
        btnRegistrar.addActionListener(e -> {
            dispose();
            new TelaRegistrarParticipacao(prof, paeg).setVisible(true);
        });

        // ============================================================
        // EXCLUIR PAEG (UC12)
        // ============================================================
        btnExcluir.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir este PAEG?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (r == JOptionPane.YES_OPTION) {
                boolean ok = Sistema.controladorPAEG.excluirPAEG(paeg, prof);

                if (ok) {
                    JOptionPane.showMessageDialog(this, "PAEG excluído com sucesso!");
                    new TelaAtividadeDetalhes(prof, atividade.getProjeto(), atividade).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Não é possível excluir: existem candidaturas vinculadas.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ============================================================
        // VOLTAR
        // ============================================================
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaAtividadeDetalhes(prof, atividade.getProjeto(), atividade).setVisible(true);
        });
    }
}
