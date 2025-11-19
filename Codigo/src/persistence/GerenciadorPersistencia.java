package persistence;

import repository.CatalogoProfessor;
import repository.CatalogoAluno;
import java.io.*;

/**
 * Gerenciador de persistência para os catálogos do sistema.
 * Responsável por salvar e carregar dados em arquivo.
 */
public class GerenciadorPersistencia {
    private static final String ARQUIVO_DADOS = "dados_sistema.dat";
    
    /**
     * Classe interna para armazenar os dados do sistema.
     */
    private static class DadosSistema implements Serializable {
        private static final long serialVersionUID = 1L;
        CatalogoProfessor catalogoProfessor;
        CatalogoAluno catalogoAluno;
        
        public DadosSistema(CatalogoProfessor catalogoProfessor, CatalogoAluno catalogoAluno) {
            this.catalogoProfessor = catalogoProfessor;
            this.catalogoAluno = catalogoAluno;
        }
    }
    
    /**
     * Salva os catálogos em arquivo.
     */
    public static void salvarDados(CatalogoProfessor catalogoProfessor, CatalogoAluno catalogoAluno) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            DadosSistema dados = new DadosSistema(catalogoProfessor, catalogoAluno);
            oos.writeObject(dados);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Carrega os catálogos do arquivo.
     * Se o arquivo não existir, retorna catálogos vazios.
     */
    public static DadosSistema carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo de dados não encontrado. Criando novos catálogos.");
            return new DadosSistema(new CatalogoProfessor(), new CatalogoAluno());
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {
            DadosSistema dados = (DadosSistema) ois.readObject();
            System.out.println("Dados carregados com sucesso.");
            return dados;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
            return new DadosSistema(new CatalogoProfessor(), new CatalogoAluno());
        }
    }
    
    /**
     * Retorna o catálogo de professores carregado.
     */
    public static CatalogoProfessor getCatalogoProfessor() {
        return carregarDados().catalogoProfessor;
    }
    
    /**
     * Retorna o catálogo de alunos carregado.
     */
    public static CatalogoAluno getCatalogoAluno() {
        return carregarDados().catalogoAluno;
    }
}
