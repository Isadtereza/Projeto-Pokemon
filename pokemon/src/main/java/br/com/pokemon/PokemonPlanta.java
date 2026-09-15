package br.com.pokemon;

public class PokemonPlanta extends Pokemon {

    public PokemonPlanta(String nome, int vida, int ataque) {
        super(nome, "Planta", vida, ataque);
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