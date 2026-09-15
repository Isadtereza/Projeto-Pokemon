package br.com.pokemon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PokedexTest {

    @Test
    public void deveRegistrarPokemonSemDuplicar() {

        Pokedex pokedex = new Pokedex();

        PokemonFogo charmander = new PokemonFogo(
                "Charmander",
                100,
                20
        );

        PokemonPlanta bulbasaur = new PokemonPlanta(
                "Bulbasaur",
                100,
                15
        );

        pokedex.registrar(charmander);
        pokedex.registrar(bulbasaur);

        // Tentando registrar Charmander novamente
        pokedex.registrar(charmander);

        assertEquals(2, pokedex.quantidadeRegistrada());

        assertTrue(
                pokedex.jaRegistrado(charmander)
        );

        assertTrue(
                pokedex.jaRegistrado(bulbasaur)
        );
    }
}