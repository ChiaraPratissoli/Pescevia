package com.example.fishmanagement.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSetup {
    private static final Logger LOGGER = Logger.getLogger(DatabaseSetup.class.getName());

    public static void init() {
        String url = "jdbc:sqlite:fishmanager.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            String sqlCreaUtenti = "CREATE TABLE IF NOT EXISTS utenti (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "ruolo TEXT NOT NULL CHECK(ruolo IN ('cliente','gestore')))";
            stmt.execute(sqlCreaUtenti);

            stmt.execute("INSERT OR IGNORE INTO utenti (username, password, ruolo) VALUES ('cliente','123','cliente')");
            stmt.execute("INSERT OR IGNORE INTO utenti (username, password, ruolo) VALUES ('gestore','123','gestore')");

            String sqlCreatePesci = "CREATE TABLE IF NOT EXISTS pesci (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL UNIQUE," +
                    "prezzo REAL NOT NULL," +
                    "quantita REAL NOT NULL)";
            stmt.execute(sqlCreatePesci);

            // Inserimento pesci di default
            stmt.execute("INSERT OR IGNORE INTO pesci (nome, prezzo, quantita) VALUES ('Orata', 35.0, 10.0)");
            stmt.execute("INSERT OR IGNORE INTO pesci (nome, prezzo, quantita) VALUES ('Sogliola', 35.0, 10.0)");
            stmt.execute("INSERT OR IGNORE INTO pesci (nome, prezzo, quantita) VALUES ('Palombo', 29.0, 8.0)");
            stmt.execute("INSERT OR IGNORE INTO pesci (nome, prezzo, quantita) VALUES ('Calamaro', 39.0, 12.0)");
            stmt.execute("INSERT OR IGNORE INTO pesci (nome, prezzo, quantita) VALUES ('Merluzzo', 29.0, 15.0)");

            LOGGER.info("Database pronto e creato se non esiste");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la creazione del database", e);
        }
    }
}
