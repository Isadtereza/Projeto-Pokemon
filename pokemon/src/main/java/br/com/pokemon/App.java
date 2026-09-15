package br.com.pokemon;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Pokemon selvagem = PokemonFactory.criarPokemonAleatorio();

System.out.println(
        "Um Pokémon selvagem apareceu!"
);

System.out.println(
        selvagem.getNome()
                + " | Tipo: "
                + selvagem.getTipo()
                + " | Vida: "
                + selvagem.getVida()
                + " | Ataque: "
                + selvagem.getAtaque()
);

        Scanner scanner = new Scanner(System.in);

        Treinador treinador = new Treinador("Ash");

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

        treinador.adicionarPokemon(charmander);
        treinador.adicionarPokemon(squirtle);

        System.out.println(
                "===== EQUIPE DE " + treinador.getNome() + " ====="
        );

        for (int i = 0; i < treinador.getEquipe().size(); i++) {

            Pokemon pokemon = treinador.getEquipe().get(i);

            System.out.println(
                    (i + 1) + " - " + pokemon.getNome()
                            + " | Tipo: " + pokemon.getTipo()
                            + " | Vida: " + pokemon.getVida()
            );
        }

        System.out.println("\nEscolha um Pokemon para batalhar:");

        int escolha = scanner.nextInt();

        if (escolha < 1 || escolha > treinador.getEquipe().size()) {
            System.out.println("Escolha invalida!");
            scanner.close();
            return;
        }

        Pokemon pokemonEscolhido =
                treinador.getEquipe().get(escolha - 1);

        System.out.println(
                "\nVoce escolheu: " + pokemonEscolhido.getNome()
        );

        // Pokemon adversario
        PokemonPlanta bulbasaur = new PokemonPlanta(
                "Bulbasaur",
                100,
                15
        );

        System.out.println(
                "O adversario sera: " + bulbasaur.getNome()
        );

        Batalha batalha = new Batalha();

        batalha.iniciar(pokemonEscolhido, bulbasaur);

        scanner.close();
    }
}