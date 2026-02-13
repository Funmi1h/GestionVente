/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author YACOUBOU
 */
import Metier.Fournisseur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDAO {
    private Connection connection;
    
    public FournisseurDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Ajouter un fournisseur
     */
    public boolean ajouter(Fournisseur fournisseur) {
        String sql = "INSERT INTO fournisseurs (nom_fournisseur, prenom_fournisseur, " +
                    "contact_fournisseur, adresse_fournisseur) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fournisseur.getNom());
            stmt.setString(2, fournisseur.getPrenom());
            stmt.setString(3, fournisseur.getTelephone());
            stmt.setString(4, fournisseur.getAdresse());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        fournisseur.setId(rs.getInt(1));
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
     * Modifier un fournisseur
     */
    public boolean modifier(Fournisseur fournisseur) {
        String sql = "UPDATE fournisseurs SET nom_fournisseur = ?, prenom_fournisseur = ?, " +
                    "contact_fournisseur = ?, adresse_fournisseur = ? " +
                    "WHERE id_fournisseur = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fournisseur.getNom());
            stmt.setString(2, fournisseur.getPrenom());
            stmt.setString(3, fournisseur.getTelephone());
            stmt.setString(4, fournisseur.getAdresse());
            stmt.setInt(5, fournisseur.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Supprimer un fournisseur
     */
    public boolean supprimer(int idFournisseur) {
        String sql = "DELETE FROM fournisseurs WHERE id_fournisseur = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFournisseur);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Lister tous les fournisseurs
     */
    public List<Fournisseur> lister() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM fournisseurs ORDER BY nom_fournisseur";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Fournisseur fournisseur = mapResultSetToFournisseur(rs);
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }
    
    /**
     * Rechercher un fournisseur par ID
     */
    public Fournisseur findById(int idFournisseur) {
        String sql = "SELECT * FROM fournisseurs WHERE id_fournisseur = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFournisseur);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFournisseur(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Rechercher des fournisseurs par nom
     */
    public List<Fournisseur> rechercherParNom(String nom) {
        List<Fournisseur> resultats = new ArrayList<>();
        String sql = "SELECT * FROM fournisseurs WHERE nom_fournisseur LIKE ? OR prenom_fournisseur LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String recherche = "%" + nom + "%";
            stmt.setString(1, recherche);
            stmt.setString(2, recherche);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultats.add(mapResultSetToFournisseur(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultats;
    }
    
    /**
     * Obtenir le nombre total de fournisseurs
     */
    public int getNombreFournisseurs() {
        String sql = "SELECT COUNT(*) as total FROM fournisseurs";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Mapper ResultSet vers Fournisseur, utilisé en interne
    private Fournisseur mapResultSetToFournisseur(ResultSet rs) throws SQLException {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(rs.getInt("id_fournisseur"));
        fournisseur.setNom(rs.getString("nom_fournisseur"));
        fournisseur.setPrenom(rs.getString("prenom_fournisseur"));
        fournisseur.setTelephone(rs.getString("contact_fournisseur"));
        fournisseur.setAdresse(rs.getString("adresse_fournisseur"));
        
        // Gestion des dates (String)
        if (rs.getTimestamp("created_at") != null) {
            fournisseur.setDateCreate(rs.getTimestamp("created_at").toString());
        }
        if (rs.getTimestamp("updated_at") != null) {
            fournisseur.setUpdatedAt(rs.getTimestamp("updated_at").toString());
        }
        
        return fournisseur;
    }
}