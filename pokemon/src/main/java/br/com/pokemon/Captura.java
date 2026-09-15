package br.com.pokemon;

public class Captura {

    private Treinador treinador;

    public Captura(Treinador treinador) {
        this.treinador = treinador;
    }

    public boolean tentarCapturar(Pokemon pokemon) {

        boolean capturou = treinador.capturarPokemon(pokemon);

        if (capturou) {
            System.out.println(
                    "🎉 Você capturou "
                            + pokemon.getNome() + "!"
            );
        } else {
            System.out.println(
                    "❌ " + pokemon.getNome()
                            + " escapou da Pokébola!"
            );
        }

        return capturou;
    }
}