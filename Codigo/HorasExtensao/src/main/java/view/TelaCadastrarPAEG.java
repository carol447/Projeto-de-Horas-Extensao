package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorPAEG;
import model.Atividade;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.EmptyBorder;

public class TelaCadastrarPAEG extends JFrame {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public TelaCadastrarPAEG(Professor prof, Atividade atividade) {
        setTitle("Cadastrar PAEG");
        setSize(500, 500); // Aumentei o tamanho
        setLocationRelativeTo(null);
        // setLayout(new GridLayout(10, 2, 10, 10)); <-- REMOVIDO!

        // ===================================
        // 1. PAINEL RAIZ COM BORDERLAYOUT
        // ===================================
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(root);

        // ===================================
        // 2. PAINEL DO FORMULÁRIO (CENTER)
        // ===================================
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        JTextField txtNome = new JTextField(20);
        JTextField txtCarga = new JTextField(10);
        JTextField txtMax = new JTextField(10);

        JTextField txtInicioCand = new JTextField("dd/MM/aaaa");
        JTextField txtFimCand = new JTextField("dd/MM/aaaa");
        JTextField txtInicioExec = new JTextField("dd/MM/aaaa");
        JTextField txtFimExec = new JTextField("dd/MM/aaaa");

        // Função para adicionar campos (Label e Text Field) em GridBagLayout
        int row = 0;
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        formPanel.add(new JLabel("Nome do PAEG:"), c);
        c.gridx = 1;
        c.gridy = row++;
        c.weightx = 1;
        formPanel.add(txtNome, c);

        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        formPanel.add(new JLabel("Carga Horária:"), c);
        c.gridx = 1;
        c.gridy = row++;
        c.weightx = 1;
        formPanel.add(txtCarga, c);

        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        formPanel.add(new JLabel("Máximo Candidaturas:"), c);
        c.gridx = 1;
        c.gridy = row++;
        c.weightx = 1;
        formPanel.add(txtMax, c);

        // --- SEPARADOR DE DATAS ---
        c.gridx = 0;
        c.gridy = row++;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 10, 0);
        formPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;

        // --- DATAS (Grid 2x2 interno no GridBagLayout) ---
        // Painel para as datas de Candidatura
        JPanel painelCand = new JPanel(new GridLayout(2, 2, 10, 5));
        painelCand.setBorder(BorderFactory.createTitledBorder("Período de Candidatura"));
        painelCand.add(new JLabel("Início:"));
        painelCand.add(txtInicioCand);
        painelCand.add(new JLabel("Fim:"));
        painelCand.add(txtFimCand);

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(painelCand, c);
        row++;

        // Painel para as datas de Execução
        JPanel painelExec = new JPanel(new GridLayout(2, 2, 10, 5));
        painelExec.setBorder(BorderFactory.createTitledBorder("Período de Execução"));
        painelExec.add(new JLabel("Início:"));
        painelExec.add(txtInicioExec);
        painelExec.add(new JLabel("Fim:"));
        painelExec.add(txtFimExec);

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(painelExec, c);
        row++;

        root.add(formPanel, BorderLayout.CENTER);

        // ===================================
        // 3. BOTÕES (SOUTH)
        // ===================================
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        Dimension buttonSize = new Dimension(100, 30);
        btnSalvar.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);

        root.add(painelBotoes, BorderLayout.SOUTH);
        btnSalvar.addActionListener(e -> {
            try {
                Date iniCand = sdf.parse(txtInicioCand.getText());
                Date fimCand = sdf.parse(txtFimCand.getText());
                Date iniExec = sdf.parse(txtInicioExec.getText());
                Date fimExec = sdf.parse(txtFimExec.getText());

                ControladorPAEG cp = Sistema.controladorPAEG;

                boolean ok = cp.criarPAEG(
                        iniCand, fimCand,
                        iniExec, fimExec,
                        txtNome.getText(),
                        Integer.parseInt(txtCarga.getText()),
                        Integer.parseInt(txtMax.getText()),
                        atividade
                );

                if (ok) {
                    JOptionPane.showMessageDialog(this, "PAEG cadastrado!");
                    Sistema.salvarDados();
                    dispose();
                    new TelaAtividadeDetalhes(prof, atividade.getProjeto(), atividade).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar.");
                }

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Datas inválidas. Use o formato dd/MM/yyyy.");
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaAtividadeDetalhes(prof, atividade.getProjeto(), atividade).setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.EncerrarSistema.encerrarAplicacao(TelaCadastrarPAEG.this);
            }
        }
        );
    }
}
