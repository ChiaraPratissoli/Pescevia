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
                        rs.getInt("quantita")
                );
                pesci.add(pesce);
            }
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Errore nel recupero dei pesci", e);
        }

        return pesci;
    }


}
