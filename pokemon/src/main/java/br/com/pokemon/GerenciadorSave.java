package br.com.pokemon;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GerenciadorSave {
    private static final String ARQUIVO_SAVE = "pokemon-save.dat";

    public static void salvar(GameSave save) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(ARQUIVO_SAVE))) {
            out.writeObject(save);
        }
    }

    public static GameSave carregar() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(ARQUIVO_SAVE))) {
            return (GameSave) in.readObject();
        }
    }

    public static boolean existeSave() {
        return new java.io.File(ARQUIVO_SAVE).exists();
    }
}
