package com.example.fishmanagement.database;

import com.example.fishmanagement.model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteDAO {
    private static final Logger LOGGER = Logger.getLogger(UtenteDAO.class.getName());

    public Utente login(String username, String password) {
        String sql = "SELECT * FROM utenti WHERE username = ? AND password = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Utente utente = new Utente(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("ruolo")
                );
                LOGGER.info("Login riuscito per utente: " + username);
                return utente;
            }
            else {
                LOGGER.warning("Login fallito per utente: " + username);
            }
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Errore SQL durante login per utente: " + username, e);
        }

        return null;
    }
}
