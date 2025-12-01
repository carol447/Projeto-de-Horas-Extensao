package view;

import com.mycompany.horasextensao.Sistema;
import model.Aluno;
import model.PAEG;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.EmptyBorder;

public class TelaDetalhesPAEGAluno extends JFrame {

    public TelaDetalhesPAEGAluno(Aluno aluno, PAEG paeg) {

        setTitle("Detalhes do PAEG: " + paeg.getNome()); // Título mais informativo
        setSize(550, 450); // Aumentei o tamanho
        setLocationRelativeTo(null);
        // setLayout(new BorderLayout()); <— Será usado no root

        // Formatação de data
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dataInicio = paeg.getDataInicialCandidatura();
        Date dataFim = paeg.getDataFinalCandidatura();

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. PAINEL DE INFORMAÇÕES (CENTER)
        // ===================================
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0); // Espaçamento vertical entre linhas
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Alinha ao topo/esquerda

        int row = 0;

        // --- NOME DO PAEG (Título Principal) ---
        JLabel lblNome = new JLabel("<html><b>PAEG:</b> <span style='font-size: 16px;'>" + paeg.getNome() + "</span></html>");
        lblNome.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = row++;
        infoPanel.add(lblNome, gbc);

        // --- SEPARADOR ---
        gbc.gridy = row++;
        gbc.insets = new Insets(10, 0, 10, 0);
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
        gbc.insets = new Insets(8, 0, 8, 0); // Reseta espaçamento

        // --- CARGA HORÁRIA e MÁXIMO DE ALUNOS ---
        JLabel lblCargaMax = new JLabel("<html><b>Carga Horária:</b> <span style='color: green;'>" + paeg.getCargaHoraria() + "h</span> | <b>Máximo de Vagas:</b> <span style='color: red;'>" + paeg.getMaximoCandidaturas() + "</span></html>");
        lblCargaMax.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = row++;
        infoPanel.add(lblCargaMax, gbc);

        // --- PROJETO E ATIVIDADE ---
        JLabel lblProjAtiv = new JLabel("<html><b>Projeto:</b> " + paeg.getAtividade().getProjeto().getNome() + " | <b>Atividade:</b> " + paeg.getAtividade().getNome() + "</html>");
        lblProjAtiv.setFont(new Font("Arial", Font.ITALIC, 14));
        gbc.gridy = row++;
        infoPanel.add(lblProjAtiv, gbc);

        // --- PERÍODO DE INSCRIÇÃO ---
        JLabel lblDatas = new JLabel(
                "<html>"
                + "<b>Período de Inscrição:</b>"
                + "<ul>"
                + "<li>Início: <span style='color: #007bff;'>" + sdf.format(dataInicio) + "</span></li>"
                + "<li>Fim: <span style='color: #dc3545;'>" + sdf.format(dataFim) + "</span></li>"
                + "</ul>"
                + "</html>"
        );
        lblDatas.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = row++;
        gbc.weighty = 1.0; // Faz este componente e o espaço restante esticar verticalmente
        infoPanel.add(lblDatas, gbc);

        root.add(new JScrollPane(infoPanel), BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JButton btnCandidatar = new JButton("Candidatar-se");
        JButton btnVoltar = new JButton("Voltar");

        // Centraliza os botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Dimension buttonSize = new Dimension(140, 30);
        btnCandidatar.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        botoes.add(btnCandidatar);
        botoes.add(btnVoltar);

        root.add(botoes, BorderLayout.SOUTH);

        btnCandidatar.addActionListener(e -> {
            boolean ok = Sistema.controladorCandidatura.candidatar(paeg, aluno);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Candidatura realizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Não foi possível realizar a candidatura.\n"
                        + "Motivos possíveis:\n"
                        + "- Já existe uma candidatura sua\n"
                        + "- Período de inscrição fechado\n"
                        + "- Número máximo de candidatos atingido",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaListarPAEGsDisponiveis(aluno).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaDetalhesPAEGAluno.this);
            }
        }
        );
    }
}
