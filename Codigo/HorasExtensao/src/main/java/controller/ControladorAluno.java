package controller;

import model.Aluno;
import catalog.CatalogoAluno;
import model.Cursos;

public class ControladorAluno {

    private final CatalogoAluno catalogoAluno;

    public ControladorAluno(CatalogoAluno catalogoAluno) {
        this.catalogoAluno = catalogoAluno;
    }

    // ======================================================
    // registrarAluno
    // ======================================================
    public boolean registrarAluno(String nome, String cpf, String ra,
            String email, String senha, Cursos curso) {

        if (!validarAlunoInfo(nome, cpf, ra, email, senha, curso)) {
            return false;
        }

        Aluno novo = new Aluno(nome, cpf, email, senha, curso, ra);

        return catalogoAluno.addAluno(novo);
    }

    // ======================================================
    // autenticarAluno
    // ======================================================
    public Aluno autenticarAluno(String email, String senha) {

        Aluno aluno = catalogoAluno.encontrarAlunoPorEmail(email);

        if (aluno != null && aluno.getSenha().equals(senha)) {
            return aluno;
        }

        return null;
    }

    // ======================================================
    // validarAutenticacao
    // ======================================================
    public boolean validarAutenticacao(String email, String senha) {

        Aluno aluno = catalogoAluno.encontrarAlunoPorEmail(email);

        return aluno != null && aluno.getSenha().equals(senha);
    }

    // ======================================================
    // validarAlunoInfo (UML: método público)
    // ======================================================
    public boolean validarAlunoInfo(String nome, String cpf, String ra,
            String email, String senha, Cursos curso) {

        // ==================== Nome ====================
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        if (nome.trim().length() < 3) {
            return false;
        }
        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$")) { // somente letras e espaços
            return false;
        }

        // ==================== CPF ====================
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }
        // Recusa CPFs óbvios como 00000000000, 11111111111 etc.
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // (opcional) validação real do dígito verificador — posso adicionar se quiser
        // ==================== RA ====================
        if (ra == null || !ra.matches("\\d+")) {
            return false;
        }
        if (ra.length() < 5) { // tamanho mínimo razoável
            return false;
        }
        if (ra.chars().allMatch(c -> c == '0')) {
            return false;
        }

        // ==================== E-mail ====================
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // email simples e seguro (não exagerado)
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[A-Za-z]{2,}$")) {
            return false;
        }

        // ==================== Senha ====================
        if (senha == null || senha.length() < 8) {
            return false;
        }
        // Pelo menos 1 minúscula, 1 maiúscula e 1 número
        if (!senha.matches(".*[a-z].*")) {
            return false;
        }
        if (!senha.matches(".*[A-Z].*")) {
            return false;
        }
        if (!senha.matches(".*\\d.*")) {
            return false;
        }

        // ==================== Curso ====================
        return curso != null;
    }

}
