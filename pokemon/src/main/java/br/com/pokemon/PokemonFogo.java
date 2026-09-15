package br.com.pokemon;

public class PokemonFogo extends Pokemon {

    public PokemonFogo(String nome, int vida, int ataque) {
        super(nome, "Fogo", vida, ataque);
    }

    @Override
    public void atacar(Pokemon adversario) {

        int dano = ataque;

        if (TipoPokemon.temVantagem(tipo, adversario.getTipo())) {
            dano = ataque * 2;
        }

        adversario.receberDano(dano);
    }
}