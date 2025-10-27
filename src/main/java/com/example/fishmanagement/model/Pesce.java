package com.example.fishmanagement.model;

public class Pesce {
    private int id;
    private String nome;
    private double prezzo;
    private double quantita;

    public Pesce(int id, String nome, double prezzo, double quantita) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public double getQuantita() {
        return quantita;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "Pesce{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", quantita=" + quantita +
                '}';
    }
}
