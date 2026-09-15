package br.com.pokemon;

public class PokemonAgua extends Pokemon {

    public PokemonAgua(String nome, int vida, int ataque) {
        super(nome, "Agua", vida, ataque);
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
