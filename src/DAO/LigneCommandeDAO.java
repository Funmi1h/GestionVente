/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Metier.LigneCommande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author YACOUBOU
 */
public class LigneCommandeDAO {
    private Connection connection;
    
    public LigneCommandeDAO(Connection connection) {
        this.connection = connection;
    }


    // Ajouter article au panier (commande en cours)
    public boolean ajouterAuPanier(LigneCommande ligne) {
        // Vérifier si l'article existe déjà dans le panier
        String Select_ligne_commande = "SELECT * FROM ligne_commande WHERE id_commande = ? AND id_article = ?";
        String updat_ligne_commande = "UPDATE ligne_commande SET quantite = quantite + ? WHERE id_commande = ? AND id_article = ?";
        String add_ligne_commande = "INSERT INTO ligne_commande (id_commande, id_article, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";
        
        try {
            // Vérifier existence
            try (PreparedStatement checkStmt = connection.prepareStatement(Select_ligne_commande)) {
                checkStmt.setInt(1, ligne.getIdCommande());
                checkStmt.setInt(2, ligne.getIdArticle());
                
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        // Mettre à jour la quantité
                        try (PreparedStatement updateStmt = connection.prepareStatement(updat_ligne_commande)) {
                            updateStmt.setInt(1, ligne.getQuantite());
                            updateStmt.setLong(2, ligne.getIdCommande());
                            updateStmt.setLong(3, ligne.getIdArticle());
                            return updateStmt.executeUpdate() > 0;
                        }
                    } else {
                        // Insérer nouvelle ligne
                        try (PreparedStatement insertStmt = connection.prepareStatement(add_ligne_commande)) {
                            insertStmt.setLong(1, ligne.getIdCommande());
                            insertStmt.setLong(2, ligne.getIdArticle());
                            insertStmt.setInt(3, ligne.getQuantite());
                            insertStmt.setDouble(4, ligne.getPrixUnitaire());
                            return insertStmt.executeUpdate() > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Récupérer les articles du panier
    /**
     * Récupérer les lignes de commande par commande
     */
    public List<LigneCommande> getLignesByCommande(int idCommande) {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT * FROM ligne_commande WHERE id_commande = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LigneCommande ligne = new LigneCommande();
                    ligne.setIdCommande(rs.getInt("id_commande"));
                    ligne.setIdArticle(rs.getInt("id_article"));
                    ligne.setQuantite(rs.getInt("quantite"));
                    ligne.setPrixUnitaire(rs.getFloat("prix_unitaire"));
                    
                    lignes.add(ligne);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lignes;
    }

    public boolean modifierQuantite(int idCommande, int idArticle, int nouvelleQuantite) {
        if (nouvelleQuantite <= 0) {
            // Si quantité <= 0, on supprime la ligne
            return supprimer(idCommande, idArticle);
        }
        
        String sql = "UPDATE ligne_commande SET quantite = ?, updated_at = NOW() " +
                    "WHERE id_commande = ? AND id_article = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nouvelleQuantite);
            stmt.setInt(2, idCommande);
            stmt.setInt(3, idArticle);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Supprimer un article d'une commande
     */
    public boolean supprimer(int idCommande, int idArticle) {
        String sql = "DELETE FROM ligne_commande WHERE id_commande = ? AND id_article = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            stmt.setInt(2, idArticle);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Supprimer tous les articles d'une commande
     */
    public boolean supprimerTout(int idCommande) {
        String sql = "DELETE FROM ligne_commande WHERE id_commande = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private LigneCommande mapResultSetToLigneCommande(ResultSet rs) throws SQLException {
        LigneCommande ligne = new LigneCommande();
        ligne.setIdCommande(rs.getInt("id_commande"));
        ligne.setIdArticle(rs.getInt("id_article"));
        ligne.setQuantite(rs.getInt("quantite"));
        ligne.setPrixUnitaire(rs.getFloat("prix_unitaire"));

        // Gestion des dates (String)
        if (rs.getTimestamp("created_at") != null) {
            ligne.setDate(rs.getTimestamp("created_at").toString());
        }

        return ligne;
    }
    
}
