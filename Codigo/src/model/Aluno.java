package model;

/**
 * Classe que representa um Aluno no sistema.
 * Herda de Usuario e adiciona atributos específicos de aluno.
 */
public class Aluno extends Usuario {
    private static final long serialVersionUID = 1L;
    
    private String ra;
    private double horasAcumuladas;
    
    public Aluno(String nome, String email, String senha, String cpf, String curso, String ra) {
        super(nome, email, senha, cpf, curso);
        this.ra = ra;
        this.horasAcumuladas = 0.0;
    }
    
    // Getters
    public String getRa() {
        return ra;
    }
    
    public double getHorasAcumuladas() {
        return horasAcumuladas;
    }
    
    // Setters
    public void setRa(String ra) {
        this.ra = ra;
    }
    
    public void setHorasAcumuladas(double horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }
    
    /**
     * Adiciona horas às horas acumuladas do aluno.
     */
    public void adicionarHoras(double horas) {
        this.horasAcumuladas += horas;
    }
    
    @Override
    public String toString() {
        return "Aluno - " + super.toString() + ", RA: " + ra + ", Horas Acumuladas: " + horasAcumuladas;
    }
}
