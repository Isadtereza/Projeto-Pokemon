package br.com.pokemon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Pokemon implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String nome;
    protected String tipo;
    protected int vida;
    protected int ataque;
    protected List<Golpe> golpes;

    public Pokemon(String nome, String tipo, int vida, int ataque) {
        this.nome = nome;
        this.tipo = tipo;
        this.vida = vida;
        this.ataque = ataque;
        this.golpes = new ArrayList<>();
    }

    public void atacar(Pokemon adversario) {
        adversario.receberDano(ataque);
    }

    public void adicionarGolpe(Golpe golpe) {

    if (golpe != null) {
        golpes.add(golpe);
    }
}

public List<Golpe> getGolpes() {
    return golpes;
}

    public void receberDano(int dano) {
        vida -= dano;

        if (vida < 0) {
            vida = 0;
        }
    }

    public int getVida() {
        return vida;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getAtaque() {
        return ataque;
    }
}