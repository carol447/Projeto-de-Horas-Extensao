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
import javax.swing.border.EmptyBorder;

public class TelaPAEGDetalhes extends JFrame {

    public TelaPAEGDetalhes(Professor prof, Atividade atividade, PAEG paeg) {

        setTitle("Detalhes do PAEG: " + paeg.getNome());
        setSize(650, 450); 
        setLocationRelativeTo(null);

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Buscar candidaturas
        List<Candidatura> todas = Sistema.catalogoCandidatura.getCandidaturas();
        long pendentes = todas.stream()
                .filter(c -> c.getPaeg().equals(paeg) && c.isPendente())
                .count();
        long aprovados = todas.stream()
                .filter(c -> c.getPaeg().equals(paeg) && c.isAprovada())
                .count();

        // ===================================
        // 2. PAINEL DE INFORMAÇÕES (CENTER) - Usando JLabel/HTML para visual limpo
        // ===================================
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Formatação das datas
        String infoText = String.format(
                "<html>"
                + "<b>Nome:</b> %s<br/>"
                + "<b>Carga Horária:</b> %dh<br/>"
                + "<b>Máximo de vagas (aprovações):</b> %d<hr/>"
                + "<b>Período de Candidatura:</b><br/>"
                + "&nbsp;- Início: %s<br/>"
                + "&nbsp;- Fim: %s<hr/>"
                + "<b>Execução:</b><br/>"
                + "&nbsp;- Início: %s<br/>"
                + "&nbsp;- Fim: %s<hr/>"
                + "<b>Candidaturas:</b><br/>"
                + "&nbsp;- Pendentes: <span style='color: orange;'>%d</span><br/>"
                + "&nbsp;- Aprovadas: <span style='color: green;'>%d</span>"
                + "</html>",
                paeg.getNome(), paeg.getCargaHoraria(), paeg.getMaximoCandidaturas(),
                sdf.format(paeg.getDataInicialCandidatura()), sdf.format(paeg.getDataFinalCandidatura()),
                sdf.format(paeg.getDataInicialExecucao()), sdf.format(paeg.getDataFinalExecucao()),
                pendentes, aprovados
        );

        JLabel lblInfo = new JLabel(infoText);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(lblInfo, gbc);

        root.add(new JScrollPane(infoPanel), BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH) - Quatro botões grandes
        // ===================================
        JButton btnCandidaturas = new JButton("Avaliar Candidaturas");
        JButton btnRegistrar = new JButton("Registrar Participação");
        JButton btnExcluir = new JButton("Excluir PAEG");
        JButton btnVoltar = new JButton("Voltar");

        // Centraliza e adiciona espaçamento aos botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));

        // Botões de 150px de largura
        Dimension buttonSize = new Dimension(130, 25);
        btnCandidaturas.setPreferredSize(buttonSize);
        btnRegistrar.setPreferredSize(buttonSize);
        btnExcluir.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        botoes.add(btnCandidaturas);
        botoes.add(btnRegistrar);
        botoes.add(btnExcluir);
        botoes.add(btnVoltar); // Posicionado corretamente

        root.add(botoes, BorderLayout.SOUTH);

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

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaPAEGDetalhes.this);
            }
        }
        );
    }
}
