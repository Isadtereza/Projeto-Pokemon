package br.com.pokemon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Treinador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private ArrayList<Pokemon> equipe;
    private Random random;

    public Treinador(String nome) {
        this.nome = nome;
        this.equipe = new ArrayList<>();
        this.random = new Random();
    }

    public Treinador(String nome, Random random) {
        this.nome = nome;
        this.equipe = new ArrayList<>();
        this.random = random;
    }

    // =====================================================
    // ADICIONAR POKÉMON À EQUIPE
    // =====================================================

    public boolean adicionarPokemon(Pokemon pokemon) {

        if (pokemon == null) {
            return false;
        }

        if (equipe.size() >= 6) {
            return false;
        }

        equipe.add(pokemon);

        return true;
    }

    // =====================================================
    // CAPTURAR POKÉMON
    // =====================================================

    public boolean capturarPokemon(Pokemon pokemon) {

        if (pokemon == null) {
            return false;
        }

        // Equipe cheia
        if (equipe.size() >= 6) {
            return false;
        }

        double chanceCaptura = calcularChanceCaptura(pokemon);

        double tentativa =
                random.nextDouble();

        if (tentativa < chanceCaptura) {

            equipe.add(pokemon);

            return true;
        }

        return false;
    }

    /**
     * Retorna a chance de captura em formato decimal, entre 0 e 1.
     */
    public double calcularChanceCaptura(Pokemon pokemon) {

        if (pokemon == null) {
            return 0.0;
        }

        double porcentagemVida = Math.max(0, pokemon.getVida()) / 100.0;

        if (porcentagemVida >= 0.75) {
            return 0.20;
        }

        if (porcentagemVida >= 0.50) {
            return 0.40;
        }

        if (porcentagemVida >= 0.25) {
            return 0.70;
        }

        return 0.90;
    }

    // =====================================================
    // INFORMAÇÕES DA EQUIPE
    // =====================================================

    public int getQuantidadePokemon() {
        return equipe.size();
    }

    public boolean equipeCheia() {
        return equipe.size() >= 6;
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public String getNome() {
        return nome;
    }

    public ArrayList<Pokemon> getEquipe() {
        return equipe;
    }
}
