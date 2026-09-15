package br.com.pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokemonFactory {

    private static final Random random = new Random();

    public static List<Pokemon> criarPokemons() {

        List<Pokemon> pokemons = new ArrayList<>();

        // FOGO
        pokemons.add(criarPokemonFogo("Ignivulp", 100, 20));
        pokemons.add(criarPokemonFogo("Cindrak", 110, 18));
        pokemons.add(criarPokemonFogo("Flamora", 95, 22));

        // AGUA
        pokemons.add(criarPokemonAgua("Aquafin", 100, 18));
        pokemons.add(criarPokemonAgua("Marivex", 90, 22));
        pokemons.add(criarPokemonAgua("Hydrillo", 115, 16));

        // PLANTA
        pokemons.add(criarPokemonPlanta("Florabbit", 100, 19));
        pokemons.add(criarPokemonPlanta("Verdillo", 110, 17));
        pokemons.add(criarPokemonPlanta("Lumifol", 90, 23));

        return pokemons;
    }

    public static Pokemon criarPokemonAleatorio() {

        List<Pokemon> pokemons =
                criarPokemons();

        int indice =
                random.nextInt(pokemons.size());

        return pokemons.get(indice);
    }

    private static PokemonFogo criarPokemonFogo(
            String nome,
            int vida,
            int ataque
    ) {
        PokemonFogo pokemon = new PokemonFogo(nome, vida, ataque);
        pokemon.adicionarGolpe(new Golpe("Brasa", "Fogo", ataque));
        pokemon.adicionarGolpe(new Golpe("Lanca Chamas", "Fogo", ataque + 10));
        pokemon.adicionarGolpe(new Golpe("Investida", "Normal", 15));
        return pokemon;
    }

    private static PokemonAgua criarPokemonAgua(
            String nome,
            int vida,
            int ataque
    ) {
        PokemonAgua pokemon = new PokemonAgua(nome, vida, ataque);
        pokemon.adicionarGolpe(new Golpe("Jato de Agua", "Agua", ataque));
        pokemon.adicionarGolpe(new Golpe("Hidro Bomba", "Agua", ataque + 10));
        pokemon.adicionarGolpe(new Golpe("Investida", "Normal", 15));
        return pokemon;
    }

    private static PokemonPlanta criarPokemonPlanta(
            String nome,
            int vida,
            int ataque
    ) {
        PokemonPlanta pokemon = new PokemonPlanta(nome, vida, ataque);
        pokemon.adicionarGolpe(new Golpe("Folha Navalha", "Planta", ataque));
        pokemon.adicionarGolpe(new Golpe("Chicote de Vinha", "Planta", ataque + 8));
        pokemon.adicionarGolpe(new Golpe("Investida", "Normal", 15));
        return pokemon;
    }
}
