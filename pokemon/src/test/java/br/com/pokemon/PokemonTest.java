package br.com.pokemon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonTest {

    @Test
    public void deveCausarDanoComVantagemDeTipo() {

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

        charmander.atacar(bulbasaur);

        assertEquals(60, bulbasaur.getVida());
    }

    @Test
    public void deveCausarDanoNormalSemVantagemDeTipo() {

        PokemonFogo charmander = new PokemonFogo(
                "Charmander",
                100,
                20
        );

        PokemonAgua squirtle = new PokemonAgua(
                "Squirtle",
                100,
                15
        );

        charmander.atacar(squirtle);

        assertEquals(80, squirtle.getVida());
    }
}