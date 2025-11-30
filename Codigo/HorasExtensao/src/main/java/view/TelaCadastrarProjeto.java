package view;

import com.mycompany.horasextensao.Sistema;
import controller.ControladorProjeto;
import model.Cursos;
import model.Professor;

import javax.swing.*;
import java.awt.*;

public class TelaCadastrarProjeto extends JFrame {

    public TelaCadastrarProjeto(Professor professor) {
        setTitle("Cadastrar Projeto");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField txtTitulo = new JTextField();
        JTextField txtDescricao = new JTextField();
        JComboBox<Cursos> cbCurso = new JComboBox<>(Cursos.values());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Descrição:"));
        add(txtDescricao);
        add(new JLabel("Curso:"));
        add(cbCurso);
        add(btnSalvar);
        add(btnVoltar);

        btnSalvar.addActionListener(e -> {
            ControladorProjeto cp = Sistema.controladorProjeto;

            boolean ok = cp.criarProjeto(
                    txtTitulo.getText(),
                    txtDescricao.getText(),
                    ((Cursos) cbCurso.getSelectedItem()).name(),
                    professor
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Projeto cadastrado!");
                Sistema.salvarDados();
                dispose();
                new TelaHomeProfessor(professor).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar projeto.");
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeProfessor(professor).setVisible(true);
        });
    }
}
