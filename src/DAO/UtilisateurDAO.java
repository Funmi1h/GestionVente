/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Metier.Client;
import Metier.Utilisateur;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author YACOUBOU
 */



public class UtilisateurDAO {
    private Connection connection;
    
    public UtilisateurDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Authentifier avec vérification de hash (version sécurisée)
     */
    public Utilisateur authentifierSecurise(String email, String motDePasseClair) {
        // Récupérer l'utilisateur par email
        String sql = "SELECT * FROM utilisateurs WHERE email = ? AND est_actif = TRUE";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String motDePasseHash = rs.getString("mot_de_passe");
                    if (verifierMotDePasse(motDePasseClair, motDePasseHash)) {
                        Utilisateur utilisateur = mapResultSetToUtilisateur(rs);
                        // Mettre à jour la dernière connexion
                        mettreAJourDerniereConnexion(utilisateur.getIdUtilisateur());
                                            // Vérifier le mot de passe (en production: utiliser BCrypt)

                        System.out.println("Authentification réussir : "+utilisateur.getEmail());
                        return utilisateur;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Authentification échoué : "+email);
        return null;
    }
    
    /**
     * Créer un nouveau compte utilisateur
     */
    public boolean creerUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (id_utilisateur, email, mot_de_passe, role, id_client) VALUES (?, ?, ?, ?, ?)";
        Client client = new ClientDAO(connection).rechercheClientParMail(utilisateur.getEmail());
        if(client==null){
            return false;
        }
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, client.getIdClient());
            stmt.setString(2, utilisateur.getEmail());
            stmt.setString(3, hashMotDePasse(utilisateur.getMotDePasse())); // Hash du mot de passe
            stmt.setString(4, utilisateur.getRole().name());
            stmt.setLong(5, client.getIdClient());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        utilisateur.setIdUtilisateur(rs.getInt(1));
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
     * Récupérer un utilisateur par email
     */
    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM utilisateurs WHERE email = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUtilisateur(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Mettre à jour la dernière connexion
     */
    private void mettreAJourDerniereConnexion(int idUtilisateur) {
        String sql = "UPDATE utilisateurs SET derniere_connexion = NOW() WHERE id_utilisateur = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Changer le mot de passe
     */
    public boolean changerMotDePasse(int idUtilisateur, String nouveauMotDePasse) {
        String sql = "UPDATE utilisateurs SET mot_de_passe = ? WHERE id_utilisateur = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hashMotDePasse(nouveauMotDePasse));
            stmt.setInt(2, idUtilisateur);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Méthodes de hashage
     */
    private String hashMotDePasse(String motDePasse) {
        return "hashed_" + motDePasse; 
    }
    
    private boolean verifierMotDePasse(String motDePasseClair, String motDePasseHash) {
        return ("hashed_" + motDePasseClair).equals(motDePasseHash);
    }
    
    /**
     * Mapper ResultSet vers Utilisateur
     */
    private Utilisateur mapResultSetToUtilisateur(ResultSet rs) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdUtilisateur(rs.getInt("id_utilisateur"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
        
        String roleStr = rs.getString("role");
        if ("ADMIN".equals(roleStr)) {
            utilisateur.setRole(Utilisateur.RoleUtilisateur.ADMIN);
        } else {
            utilisateur.setRole(Utilisateur.RoleUtilisateur.CLIENT);
        }
        
        utilisateur.setIdClient(rs.getInt("id_client"));
        if (rs.wasNull()) {
            utilisateur.setIdClient(0);
        }
        
        utilisateur.setEstActif(rs.getBoolean("est_actif"));
        
        java.sql.Timestamp derniereConnexion = rs.getTimestamp("derniere_connexion");
        if (derniereConnexion != null) {
            utilisateur.setDerniereConnexion(derniereConnexion.toString());
        }
        
        return utilisateur;
    }
}