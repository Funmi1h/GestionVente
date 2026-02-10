/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author YACOUBOU
 */
import Metier.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {
    private Connection connection;
    
    public CategorieDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Ajouter une catégorie
     */
    public boolean ajouter(Categorie categorie) {
        String sql = "INSERT INTO categories (nom_categorie, description) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categorie.getNomCategorie());
            stmt.setString(2, categorie.getDescription());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        categorie.setIdCategorie(rs.getInt(1));
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
     * Modifier une catégorie
     */
    public boolean modifier(Categorie categorie) {
        String sql = "UPDATE categories SET nom_categorie = ?, description = ? " +
                    "WHERE id_categorie = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categorie.getNomCategorie());
            stmt.setString(2, categorie.getDescription());
            stmt.setInt(3, categorie.getIdCategorie());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Supprimer une catégorie
     */
    public boolean supprimer(int idCategorie) {
        // D'abord supprimer les associations dans article_categorie
        String sqlDeleteAssoc = "DELETE FROM article_categorie WHERE id_categorie = ?";
        String sqlDeleteCat = "DELETE FROM categories WHERE id_categorie = ?";
        
        try {
            // Supprimer les associations
            try (PreparedStatement stmtAssoc = connection.prepareStatement(sqlDeleteAssoc)) {
                stmtAssoc.setInt(1, idCategorie);
                stmtAssoc.executeUpdate();
            }
            
            // Supprimer la catégorie
            try (PreparedStatement stmtCat = connection.prepareStatement(sqlDeleteCat)) {
                stmtCat.setInt(1, idCategorie);
                return stmtCat.executeUpdate() > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Lister toutes les catégories
     */
    public List<Categorie> lister() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY nom_categorie";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                categories.add(mapResultSetToCategorie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    
    /**
     * Rechercher une catégorie par ID
     */
    public Categorie findById(int idCategorie) {
        String sql = "SELECT * FROM categories WHERE id_categorie = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategorie);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategorie(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Rechercher des catégories par nom
     */
    public List<Categorie> rechercherParNom(String nom) {
        List<Categorie> resultats = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE nom_categorie LIKE ? ORDER BY nom_categorie";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nom + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultats.add(mapResultSetToCategorie(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultats;
    }
    
    /**
     * Ajouter un article à une catégorie
     */
    public boolean ajouterArticleACategorie(int idArticle, int idCategorie) {
        String sql = "INSERT INTO article_categorie (id_article, id_categorie) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idArticle);
            stmt.setInt(2, idCategorie);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Supprimer un article d'une catégorie
     */
    public boolean supprimerArticleDeCategorie(int idArticle, int idCategorie) {
        String sql = "DELETE FROM article_categorie WHERE id_article = ? AND id_categorie = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idArticle);
            stmt.setInt(2, idCategorie);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Obtenir les catégories d'un article
     */
    public List<Categorie> getCategoriesByArticle(int idArticle) {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT c.* FROM categories c " +
                    "INNER JOIN article_categorie ac ON c.id_categorie = ac.id_categorie " +
                    "WHERE ac.id_article = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idArticle);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategorie(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    
    /**
     * Obtenir le nombre d'articles par catégorie
     */
    public int getNombreArticlesParCategorie(int idCategorie) {
        String sql = "SELECT COUNT(*) as total FROM article_categorie WHERE id_categorie = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategorie);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Mapper ResultSet vers Categorie
     */
    private Categorie mapResultSetToCategorie(ResultSet rs) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setIdCategorie(rs.getInt("id_categorie"));
        categorie.setNomCategorie(rs.getString("nom_categorie"));
        categorie.setDescription(rs.getString("description"));
        
        if (rs.getTimestamp("created_at") != null) {
            categorie.setCreatedAt(rs.getTimestamp("created_at").toString());
        }
        if (rs.getTimestamp("updated_at") != null) {
            categorie.setUpdatedAt(rs.getTimestamp("updated_at").toString());
        }
        
        return categorie;
    }
}