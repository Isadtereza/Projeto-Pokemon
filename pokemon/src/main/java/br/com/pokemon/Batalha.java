package br.com.pokemon;

public class Batalha {

    public void iniciar(Pokemon pokemon1, Pokemon pokemon2) {

        System.out.println("===== BATALHA =====");

        System.out.println(
                pokemon1.getNome() + " VS " + pokemon2.getNome()
        );

        int turno = 1;

        while (pokemon1.getVida() > 0 && pokemon2.getVida() > 0) {

            System.out.println("\n--- Turno " + turno + " ---");

            pokemon1.atacar(pokemon2);

            System.out.println(
                    pokemon1.getNome() + " atacou!"
            );

            System.out.println(
                    "Vida de " + pokemon2.getNome() + ": "
                            + pokemon2.getVida()
            );

            if (pokemon2.getVida() <= 0) {
                break;
            }

            pokemon2.atacar(pokemon1);

            System.out.println(
                    pokemon2.getNome() + " atacou!"
            );

            System.out.println(
                    "Vida de " + pokemon1.getNome() + ": "
                            + pokemon1.getVida()
            );

            turno++;
        }

        System.out.println("\n===== FIM DA BATALHA =====");

        if (pokemon1.getVida() > 0) {
            System.out.println(
                    "Vencedor: " + pokemon1.getNome()
            );
        } else {
            System.out.println(
                    "Vencedor: " + pokemon2.getNome()
            );
        }
    }
}