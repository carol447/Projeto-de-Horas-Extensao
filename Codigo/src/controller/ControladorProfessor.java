package controller;

import model.Professor;
import repository.CatalogoProfessor;
import java.util.regex.Pattern;

/**
 * Controlador responsável pelo gerenciamento de professores.
 */
public class ControladorProfessor {
    private CatalogoProfessor catalogo;
    
    public ControladorProfessor(CatalogoProfessor catalogo) {
        this.catalogo = catalogo;
    }
    
    /**
     * Registra um novo professor no sistema.
     * Retorna mensagem de sucesso ou erro.
     */
    public String registrarProf(String nome, String email, String senha, String cpf, String curso) {
        String validacao = validarProfInfo(nome, email, senha, cpf, curso);
        if (!validacao.equals("OK")) {
            return validacao;
        }
        
        // Verificar se já existe professor com mesmo email
        if (catalogo.encontrarProfPorEmail(email) != null) {
            return "Erro: Já existe um professor cadastrado com este email.";
        }
        
        // Verificar se já existe professor com mesmo CPF
        if (catalogo.encontrarProfPorCpf(cpf) != null) {
            return "Erro: Já existe um professor cadastrado com este CPF.";
        }
        
        Professor novoProfessor = new Professor(nome, email, senha, cpf, curso);
        catalogo.addProf(novoProfessor);
        return "Professor cadastrado com sucesso!";
    }
    
    /**
     * Autentica um professor no sistema.
     * Retorna o Professor autenticado ou null se falhar.
     */
    public Professor autenticarProf(String email, String senha) {
        String validacao = validarAutenticacao(email, senha);
        if (!validacao.equals("OK")) {
            return null;
        }
        
        Professor professor = catalogo.encontrarProfPorEmail(email);
        if (professor == null) {
            return null;
        }
        
        if (professor.getSenha().equals(senha)) {
            return professor;
        }
        
        return null;
    }
    
    /**
     * Valida os dados de autenticação.
     */
    public String validarAutenticacao(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            return "Erro: Email não pode estar vazio.";
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            return "Erro: Senha não pode estar vazia.";
        }
        
        return "OK";
    }
    
    /**
     * Valida os dados de um professor.
     */
    public String validarProfInfo(String nome, String email, String senha, String cpf, String curso) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Erro: Nome não pode estar vazio.";
        }
        
        if (email == null || email.trim().isEmpty()) {
            return "Erro: Email não pode estar vazio.";
        }
        
        if (!validarEmail(email)) {
            return "Erro: Email inválido.";
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            return "Erro: Senha não pode estar vazia.";
        }
        
        if (senha.length() < 6) {
            return "Erro: Senha deve ter no mínimo 6 caracteres.";
        }
        
        if (cpf == null || cpf.trim().isEmpty()) {
            return "Erro: CPF não pode estar vazio.";
        }
        
        if (!validarCPF(cpf)) {
            return "Erro: CPF inválido.";
        }
        
        if (curso == null || curso.trim().isEmpty()) {
            return "Erro: Curso não pode estar vazio.";
        }
        
        return "OK";
    }
    
    /**
     * Valida formato de email.
     */
    private boolean validarEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    
    /**
     * Valida formato de CPF (apenas formato, sem verificação de dígito).
     */
    private boolean validarCPF(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return cpfLimpo.length() == 11;
    }
}
