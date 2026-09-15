package br.com.pokemon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pokedex implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Pokemon> registros;

    public Pokedex() {
        registros = new ArrayList<>();
    }

    public void registrar(Pokemon pokemon) {

        if (pokemon != null && !jaRegistrado(pokemon)) {
            registros.add(pokemon);
        }
    }

    public boolean jaRegistrado(Pokemon pokemon) {

        if (pokemon == null) {
            return false;
        }

        for (Pokemon p : registros) {

            if (p.getNome().equals(pokemon.getNome())) {
                return true;
            }
        }

        return false;
    }

    public int quantidadeRegistrada() {
        return registros.size();
    }

    public List<Pokemon> getRegistros() {
        return registros;
    }

    public void limpar() {
        registros.clear();
    }
}
