/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Paiement;
import java.sql.Connection;

/**
 *
 * @author YACOUBOU
 */
import Metier.Paiement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAO {
    private Connection connection;
    
    public PaiementDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Ajouter un paiement
     */
    public boolean ajouter(Paiement paiement) {
        String sql = "INSERT INTO paiements (montant, mode_paiement, id_commande) " +
                    "VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //stmt.setString(1, paiement.getDatePaiement());
            stmt.setDouble(1, paiement.getMontant());
            stmt.setString(2, paiement.getModePaiement());
            stmt.setInt(3, paiement.getIdCommande());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        paiement.setIdPaiement(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Modifier un paiement
     */
    public boolean modifier(Paiement paiement) {
        String sql = "UPDATE paiements SET date_paiement = ?, montant = ?, " +
                    "mode_paiement = ? WHERE id_paiement = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paiement.getDatePaiement());
            stmt.setDouble(2, paiement.getMontant());
            stmt.setString(3, paiement.getModePaiement());
            stmt.setInt(4, paiement.getIdPaiement());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Supprimer un paiement
     */
    public boolean supprimer(int idPaiement) {
        String sql = "DELETE FROM paiements WHERE id_paiement = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPaiement);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Lister tous les paiements
     */
    public List<Paiement> listerTous() {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiements ORDER BY date_paiement DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                paiements.add(mapResultSetToPaiement(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paiements;
    }
    
    /**
     * Rechercher un paiement par ID
     */
    public Paiement findById(int idPaiement) {
        String sql = "SELECT * FROM paiements WHERE id_paiement = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPaiement);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaiement(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Rechercher un paiement par ID de commande
     */
    public Paiement findByCommandeId(int idCommande) {
        String sql = "SELECT * FROM paiements WHERE id_commande = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaiement(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Lister les paiements par mode de paiement
     */
    public List<Paiement> listerParMode(String modePaiement) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiements WHERE mode_paiement = ? ORDER BY date_paiement DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, modePaiement);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapResultSetToPaiement(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paiements;
    }
    
    /**
     * Obtenir le total des paiements
     */
    public double getTotalPaiements() {
        String sql = "SELECT SUM(montant) as total FROM paiements";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    /**
     * Obtenir les statistiques par mode de paiement
     */
    public java.util.Map<String, Double> getStatistiquesParMode() {
        java.util.Map<String, Double> stats = new java.util.HashMap<>();
        String sql = "SELECT mode_paiement, SUM(montant) as total " +
                    "FROM paiements GROUP BY mode_paiement";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String mode = rs.getString("mode_paiement");
                double total = rs.getDouble("total");
                stats.put(mode, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
    
    /**
     * Mapper ResultSet vers Paiement
     */
    private Paiement mapResultSetToPaiement(ResultSet rs) throws SQLException {
        Paiement paiement = new Paiement();
        paiement.setIdPaiement(rs.getInt("id_paiement"));
        paiement.setDatePaiement(rs.getString("date_paiement"));
        paiement.setMontant(rs.getDouble("montant"));
        paiement.setModePaiement(rs.getString("mode_paiement"));
        paiement.setIdCommande(rs.getInt("id_commande"));
        
        return paiement;
    }
}
