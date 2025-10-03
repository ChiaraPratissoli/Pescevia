package com.example.fishmanagement.model;

public class Utente {
    private final int id;
    private final String username;
    private final String password;
    private final String ruolo;

    public Utente(int id, String username, String password, String ruolo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }
}
