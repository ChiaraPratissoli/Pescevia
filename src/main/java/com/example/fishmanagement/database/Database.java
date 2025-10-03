package com.example.fishmanagement.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final String URL = "jdbc:sqlite:fishmanager.db";
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    public static Connection getConnection() throws SQLException {
        try{
            Connection connection = DriverManager.getConnection(URL);
            LOGGER.info("Connessione al database riuscita");
            return connection;
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Errore durante la connessione al database", e);
            throw e;
        }
    }
}
