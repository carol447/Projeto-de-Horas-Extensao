package controller;

import model.Aluno;
import repository.CatalogoAluno;
import java.util.regex.Pattern;

/**
 * Controlador responsável pelo gerenciamento de alunos.
 */
public class ControladorAluno {
    private CatalogoAluno catalogo;
    
    public ControladorAluno(CatalogoAluno catalogo) {
        this.catalogo = catalogo;
    }
    
    /**
     * Registra um novo aluno no sistema.
     * Retorna mensagem de sucesso ou erro.
     */
    public String registrarAluno(String nome, String email, String senha, String cpf, String curso, String ra) {
        String validacao = validarAlunoInfo(nome, email, senha, cpf, curso, ra);
        if (!validacao.equals("OK")) {
            return validacao;
        }
        
        // Verificar se já existe aluno com mesmo email
        if (catalogo.encontrarAlunoPorEmail(email) != null) {
            return "Erro: Já existe um aluno cadastrado com este email.";
        }
        
        // Verificar se já existe aluno com mesmo CPF
        if (catalogo.encontrarAlunoPorCpf(cpf) != null) {
            return "Erro: Já existe um aluno cadastrado com este CPF.";
        }
        
        // Verificar se já existe aluno com mesmo RA
        if (catalogo.encontrarAlunoPorRa(ra) != null) {
            return "Erro: Já existe um aluno cadastrado com este RA.";
        }
        
        Aluno novoAluno = new Aluno(nome, email, senha, cpf, curso, ra);
        catalogo.addAluno(novoAluno);
        return "Aluno cadastrado com sucesso!";
    }
    
    /**
     * Autentica um aluno no sistema.
     * Retorna o Aluno autenticado ou null se falhar.
     */
    public Aluno autenticarAluno(String email, String senha) {
        String validacao = validarAutenticacao(email, senha);
        if (!validacao.equals("OK")) {
            return null;
        }
        
        Aluno aluno = catalogo.encontrarAlunoPorEmail(email);
        if (aluno == null) {
            return null;
        }
        
        if (aluno.getSenha().equals(senha)) {
            return aluno;
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
     * Valida os dados de um aluno.
     */
    public String validarAlunoInfo(String nome, String email, String senha, String cpf, String curso, String ra) {
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
        
        if (ra == null || ra.trim().isEmpty()) {
            return "Erro: RA não pode estar vazio.";
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
