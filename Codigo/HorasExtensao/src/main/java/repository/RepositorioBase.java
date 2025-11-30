package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositorioBase<T> {

    private final File arquivo;

    protected RepositorioBase(String filePath) {
        this.arquivo = new File(filePath);

        // ========== CRIA DIRETÓRIO AUTOMATICAMENTE ==========
        File pasta = arquivo.getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
        // =====================================================
    }

    @SuppressWarnings("unchecked")
    public List<T> carregar() {
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void salvar(List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + arquivo.getName());
        }
    }
}
