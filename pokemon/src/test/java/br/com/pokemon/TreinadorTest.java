package br.com.pokemon;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TreinadorTest {

    @Test
    public void deveCapturarPokemonComVidaBaixa() {

        // Random controlado para garantir que a captura aconteça
        Random random = new Random() {
            @Override
            public double nextDouble() {
                return 0.10;
            }
        };

        Treinador treinador = new Treinador("Ash", random);

        PokemonFogo charmander = new PokemonFogo(
                "Charmander",
                100,
                20
        );

        // Charmander fica com 20% da vida
        charmander.receberDano(80);

        boolean capturou = treinador.capturarPokemon(charmander);

        assertTrue(capturou);
        assertEquals(1, treinador.getEquipe().size());
    }

@Test
public void deveFalharAoCapturarPokemon() {

    Random random = new Random() {
        @Override
        public double nextDouble() {
            return 0.95;
        }
    };

    Treinador treinador = new Treinador("Ash", random);

    PokemonFogo charmander = new PokemonFogo(
            "Charmander",
            100,
            20
    );

    // Charmander fica com 20% da vida
    charmander.receberDano(80);

    boolean capturou = treinador.capturarPokemon(charmander);

    assertFalse(capturou);
    assertEquals(0, treinador.getEquipe().size());
}
}