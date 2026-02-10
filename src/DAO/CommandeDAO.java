/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author YACOUBOU
 */
import Metier.Commande;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {
    private Connection connection;
    
    public CommandeDAO(Connection connection) {
        this.connection = connection;
    }
    
    // Créer une commande (panier)
    public boolean creerPanier(int idClient) {
        String sql = "INSERT INTO commandes (id_client, statut) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            stmt.setString(2, Commande.STATUT_EN_COURS);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Récupérer le panier d'un client
    public Commande getPanierByClient(int idClient) {
        String sql = "SELECT * FROM commandes WHERE id_client = ? AND statut = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            stmt.setString(2, Commande.STATUT_EN_COURS);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Commande commande = new Commande();
                    commande.setIdCommande(rs.getInt("id_commande"));
                    commande.setIdClient(rs.getInt("id_client"));
                    commande.setStatut(rs.getString("statut"));
                    commande.setDateCommande(rs.getString("date_commande"));
                    
                    return commande;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Valider le panier (changer statut)
    public boolean validerPanier(int idCommande) {
        String sql = "UPDATE commandes SET statut = ? WHERE id_commande = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Commande.STATUT_PAYEE);
            stmt.setInt(2, idCommande);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

public List<Commande> getCommandesByClient(int idClient) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commandes WHERE id_client = ? AND statut != ? " +
                    "ORDER BY date_commande DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            stmt.setString(2, Commande.STATUT_EN_COURS); // Exclure le panier
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    commandes.add(mapResultSetToCommande(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }
    
    /**
     * Lister toutes les commandes (pour admin)
     */
    public List<Commande> listerToutes() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commandes WHERE statut != ? ORDER BY date_commande DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Commande.STATUT_EN_COURS); // Exclure les paniers
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    commandes.add(mapResultSetToCommande(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }
    
    /**
     * Changer le statut d'une commande
     */
    public boolean changerStatut(int idCommande, String nouveauStatut) {
        String sql = "UPDATE commandes SET statut = ?, updated_at = NOW() " +
                    "WHERE id_commande = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nouveauStatut);
            stmt.setInt(2, idCommande);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
     // Mapper ResultSet vers Commande
    private Commande mapResultSetToCommande(ResultSet rs) throws SQLException {
        Commande commande = new Commande();
        commande.setIdCommande(rs.getInt("id_commande"));
        commande.setDateCommande(rs.getString("date_commande"));
        commande.setStatut(rs.getString("statut"));
        commande.setIdClient(rs.getInt("id_client"));
        
        return commande;
    }
    
}