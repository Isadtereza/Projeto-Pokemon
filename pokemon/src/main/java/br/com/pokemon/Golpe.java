package br.com.pokemon;

import java.io.Serializable;

public class Golpe implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String tipo;
    private int dano;

    public Golpe(String nome, String tipo, int dano) {
        this.nome = nome;
        this.tipo = tipo;
        this.dano = dano;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getDano() {
        return dano;
    }
}