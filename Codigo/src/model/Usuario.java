package model;

import java.io.Serializable;

/**
 * Classe abstrata base para representar um usuário do sistema.
 * Implementa Serializable para permitir persistência.
 */
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String curso;
    
    public Usuario(String nome, String email, String senha, String cpf, String curso) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.curso = curso;
    }
    
    // Getters
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public String getCurso() {
        return curso;
    }
    
    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public void setCurso(String curso) {
        this.curso = curso;
    }
    
    @Override
    public String toString() {
        return "Nome: " + nome + ", Email: " + email + ", CPF: " + cpf + ", Curso: " + curso;
    }
}
