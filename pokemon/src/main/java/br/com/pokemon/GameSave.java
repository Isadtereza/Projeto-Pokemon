package br.com.pokemon;

import java.io.Serializable;

public class GameSave implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Treinador treinador;
    private final Pokedex pokedex;
    private final Pokemon pokemonEscolhido;
    private final Pokemon selvagem;
    private final String nomeTreinador;
    private final String cenario;

    public GameSave(Treinador treinador, Pokedex pokedex,
                    Pokemon pokemonEscolhido, Pokemon selvagem,
                    String nomeTreinador, String cenario) {
        this.treinador = treinador;
        this.pokedex = pokedex;
        this.pokemonEscolhido = pokemonEscolhido;
        this.selvagem = selvagem;
        this.nomeTreinador = nomeTreinador;
        this.cenario = cenario;
    }

    public Treinador getTreinador() { return treinador; }
    public Pokedex getPokedex() { return pokedex; }
    public Pokemon getPokemonEscolhido() { return pokemonEscolhido; }
    public Pokemon getSelvagem() { return selvagem; }
    public String getNomeTreinador() { return nomeTreinador; }
    public String getCenario() { return cenario; }
}
