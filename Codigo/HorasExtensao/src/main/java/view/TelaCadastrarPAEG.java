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

public class TelaCadastrarPAEG extends JFrame {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public TelaCadastrarPAEG(Professor prof, Atividade atividade) {
        setTitle("Cadastrar PAEG");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(10, 2, 10, 10));

        JTextField txtNome = new JTextField();
        JTextField txtCarga = new JTextField();
        JTextField txtMax = new JTextField();

        JTextField txtInicioCand = new JTextField("dd/MM/aaaa");
        JTextField txtFimCand = new JTextField("dd/MM/aaaa");
        JTextField txtInicioExec = new JTextField("dd/MM/aaaa");
        JTextField txtFimExec = new JTextField("dd/MM/aaaa");

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        add(new JLabel("Nome do PAEG:"));
        add(txtNome);
        add(new JLabel("Carga Horária:"));
        add(txtCarga);
        add(new JLabel("Máximo Candidaturas:"));
        add(txtMax);
        add(new JLabel("Início Candidatura:"));
        add(txtInicioCand);
        add(new JLabel("Fim Candidatura:"));
        add(txtFimCand);
        add(new JLabel("Início Execução:"));
        add(txtInicioExec);
        add(new JLabel("Fim Execução:"));
        add(txtFimExec);
        add(btnSalvar);
        add(btnVoltar);

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
    }
}
