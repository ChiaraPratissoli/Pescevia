package com.example.fishmanagement.database;

import com.example.fishmanagement.model.Pesce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PesceDAO {
    private static final Logger LOGGER = Logger.getLogger(PesceDAO.class.getName());

    public List<Pesce> getAll(){
        List<Pesce> pesci = new ArrayList<>();
        String sql = "SELECT * FROM pesci";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()){
                Pesce pesce = new Pesce(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("prezzo"),
                        rs.getDouble("quantita")
                );
                pesci.add(pesce);
            }
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Errore nel recupero dei pesci", e);
        }

        return pesci;
    }

    public boolean addPesce(Pesce pesce){
        String sql = "INSERT INTO pesci (nome, prezzo, quantita) VALUES (?, ?, ?)";

        try(Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, pesce.getNome());
            pstmt.setDouble(2, pesce.getPrezzo());
            pstmt.setDouble(3, pesce.getQuantita());

            return pstmt.executeUpdate() > 0;
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Errore nell'aggiunta del pesce", e);
            return false;
        }
    }

    public boolean update(Pesce pesce){
        String sql = "UPDATE pesci SET nome = ?, PREZZO = ?, QUANTITA = ? WHERE ID = ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, pesce.getNome());
            pstmt.setDouble(2, pesce.getPrezzo());
            pstmt.setDouble(3, pesce.getQuantita());
            pstmt.setInt(4, pesce.getId());

            return pstmt.executeUpdate() > 0;
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Errore nell'aggiornamento del pesce", e);
            return false;
        }
    }

    public boolean delete(int id){
        String sql = "DELETE FROM pesci WHERE id = ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Errore nell'eliminazione del pesce", e);
            return false;
        }
    }

    public boolean buy(Pesce pesce){
        String sql = "UPDATE pesci SET quantita = quantita - ? WHERE id = ? AND quantita >= ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            conn.setAutoCommit(false);

            pstmt.setDouble(1, pesce.getQuantita());
            pstmt.setInt(2,pesce.getId());
            pstmt.setDouble(3, pesce.getQuantita());

            int rows = pstmt.executeUpdate();
            conn.commit();

            return rows > 0;

        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Errore durante l'acquisto del pesce con id: " + pesce.getId(), e);
            return false;
        }
    }

    public void restoreQuantity(Pesce pesce) {
        String sql = "UPDATE pesci SET quantita = quantita + ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, pesce.getQuantita());
            pstmt.setInt(2, pesce.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il ripristino della quantità", e);
        }
    }
}
