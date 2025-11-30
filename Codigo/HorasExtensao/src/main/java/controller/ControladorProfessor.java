package controller;

import catalog.CatalogoProfessor;
import model.Cursos;
import model.Professor;

public class ControladorProfessor {

    private final CatalogoProfessor catalogoProfessor;

    public ControladorProfessor(CatalogoProfessor catalogoProfessor) {
        this.catalogoProfessor = catalogoProfessor;
    }


    public boolean registrarProf(String nome, String cpf, String email,
                                 String senha, Cursos curso) {

        if (!validarProfInfo(nome, cpf, email, senha, curso)) {
            return false;
        }

        Professor novo = new Professor(nome, cpf, email, senha, curso);
        return catalogoProfessor.addProf(novo);
    }

    public Professor autenticarProf(String email, String senha) {
        Professor encontrado = catalogoProfessor.encontrarProfPorEmail(email);

        if (encontrado != null && encontrado.getSenha().equals(senha)) {
            return encontrado;
        }
        return null;
    }


    public boolean validarAutenticacao(String email, String senha) {
        Professor encontrado = catalogoProfessor.encontrarProfPorEmail(email);

        return encontrado != null && encontrado.getSenha().equals(senha);
    }


    public boolean validarProfInfo(String nome, String cpf, String email,
                               String senha, Cursos curso) {

    // ==================== Nome ====================
    if (nome == null || nome.trim().isEmpty()) {
        return false;
    }
    if (nome.trim().length() < 3) {
        return false;
    }
    if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$")) { 
        return false;
    }

    // ==================== CPF ====================
    if (cpf == null || !cpf.matches("\\d{11}")) {
        return false;
    }
    // evitar números repetidos (00000000000 etc.)
    if (cpf.chars().distinct().count() == 1) {
        return false;
    }

    // aqui posso adicionar validação completa do dígito verificador, se quiser

    // ==================== E-mail ====================
    if (email == null || email.trim().isEmpty()) {
        return false;
    }
    if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[A-Za-z]{2,}$")) {
        return false;
    }

    // ==================== Senha ====================
    if (senha == null || senha.length() < 8) {
        return false;
    }
    if (!senha.matches(".*[a-z].*")) {  // minúscula
        return false;
    }
    if (!senha.matches(".*[A-Z].*")) {  // maiúscula
        return false;
    }
    if (!senha.matches(".*\\d.*")) {    // número
        return false;
    }

    // ==================== Curso ====================
    return curso != null;
}

}
