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

            String sqlCreate = "CREATE TABLE IF NOT EXISTS utenti (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "ruolo TEXT NOT NULL CHECK(ruolo IN ('cliente','gestore')))";
            stmt.execute(sqlCreate);

            String sqlInsert1 = "INSERT OR IGNORE INTO utenti (username, password, ruolo) VALUES ('cliente','123','cliente')";
            String sqlInsert2 = "INSERT OR IGNORE INTO utenti (username, password, ruolo) VALUES ('gestore','123','gestore')";
            stmt.execute(sqlInsert1);
            stmt.execute(sqlInsert2);

            LOGGER.info("Database pronto e creato se non esiste");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la creazione del database", e);
        }
    }
}
