package br.com.pokemon;

public class TipoPokemon {

    private TipoPokemon() {
        // Classe utilitária: não deve ser instanciada.
    }

    public static boolean temVantagem(String tipoAtacante, String tipoDefensor) {

        if (tipoAtacante == null || tipoDefensor == null) {
            return false;
        }

        return (tipoAtacante.equals("Fogo") && tipoDefensor.equals("Planta"))
                || (tipoAtacante.equals("Agua") && tipoDefensor.equals("Fogo"))
                || (tipoAtacante.equals("Planta") && tipoDefensor.equals("Agua"));
    }
}
