/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles;

/**
 *
 * @author Héloïse
 */
import Metier.Categorie;
import DAO.CategorieDAO;
import java.sql.Connection;
import java.util.List;

public class CategorieModel {
    private CategorieDAO categorieDAO;
    
    public CategorieModel(Connection connection) {
        this.categorieDAO = new CategorieDAO(connection);
    }
    
    /**
     * Ajouter une catégorie
     */
    public boolean ajouterCategorie(Categorie categorie) {
        // Validation des données
        if (categorie.getNomCategorie() == null || categorie.getNomCategorie().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la catégorie est obligatoire");
        }
        
        return categorieDAO.ajouter(categorie);
    }
    
    /**
     * Modifier une catégorie
     */
    public boolean modifierCategorie(Categorie categorie) {
        if (categorie.getIdCategorie() <= 0) {
            throw new IllegalArgumentException("ID catégorie invalide");
        }
        
        // Validation des données
        if (categorie.getNomCategorie() == null || categorie.getNomCategorie().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la catégorie est obligatoire");
        }
        
        return categorieDAO.modifier(categorie);
    }
    
    /**
     * Supprimer une catégorie
     */
    public boolean supprimerCategorie(int idCategorie) {
        if (idCategorie <= 0) {
            throw new IllegalArgumentException("ID catégorie invalide");
        }
        
        // Vérifier s'il y a des articles dans cette catégorie
        int nbArticles = categorieDAO.getNombreArticlesParCategorie(idCategorie);
        if (nbArticles > 0) {
            throw new IllegalStateException("Impossible de supprimer: " + nbArticles + 
                                          " article(s) associé(s) à cette catégorie");
        }
        
        return categorieDAO.supprimer(idCategorie);
    }
    
    /**
     * Récupérer toutes les catégories
     */
    public List<Categorie> listerToutesCategories() {
        return categorieDAO.lister();
    }
    
    /**
     * Rechercher une catégorie par ID
     */
    public Categorie trouverCategorieParId(int idCategorie) {
        if (idCategorie <= 0) {
            throw new IllegalArgumentException("ID catégorie invalide");
        }
        
        return categorieDAO.findById(idCategorie);
    }
    
    /**
     * Rechercher des catégories par nom
     */
    public List<Categorie> rechercherCategoriesParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de recherche est obligatoire");
        }
        
        return categorieDAO.rechercherParNom(nom);
    }
    
    /**
     * Ajouter un article à une catégorie
     */
    public boolean ajouterArticleACategorie(int idArticle, int idCategorie) {
        if (idArticle <= 0 || idCategorie <= 0) {
            throw new IllegalArgumentException("ID article ou catégorie invalide");
        }
        
        return categorieDAO.ajouterArticleACategorie(idArticle, idCategorie);
    }
    
    /**
     * Supprimer un article d'une catégorie
     */
    public boolean supprimerArticleDeCategorie(int idArticle, int idCategorie) {
        if (idArticle <= 0 || idCategorie <= 0) {
            throw new IllegalArgumentException("ID article ou catégorie invalide");
        }
        
        return categorieDAO.supprimerArticleDeCategorie(idArticle, idCategorie);
    }
    
    /**
     * Récupérer les catégories d'un article
     */
    public List<Categorie> getCategoriesByArticle(int idArticle) {
        if (idArticle <= 0) {
            throw new IllegalArgumentException("ID article invalide");
        }
        
        return categorieDAO.getCategoriesByArticle(idArticle);
    }
    
    /**
     * Vérifier si une catégorie existe
     */
    public boolean categorieExiste(String nomCategorie) {
        if (nomCategorie == null || nomCategorie.trim().isEmpty()) {
            return false;
        }
        
        List<Categorie> categories = categorieDAO.lister();
        return categories.stream()
                .anyMatch(c -> nomCategorie.equalsIgnoreCase(c.getNomCategorie()));
    }
    
    /**
     * Obtenir le nombre total de catégories
     */
    public int getNombreTotalCategories() {
        return categorieDAO.lister().size();
    }
    
    /**
     * Obtenir le nombre d'articles par catégorie
     */
    public int getNombreArticlesParCategorie(int idCategorie) {
        return categorieDAO.getNombreArticlesParCategorie(idCategorie);
    }
    
    /**
     * Obtenir les statistiques des catégories
     */
    public java.util.Map<String, Integer> getStatistiquesCategories() {
        List<Categorie> categories = categorieDAO.lister();
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        
        for (Categorie categorie : categories) {
            int nbArticles = categorieDAO.getNombreArticlesParCategorie(categorie.getIdCategorie());
            stats.put(categorie.getNomCategorie(), nbArticles);
        }
        
        return stats;
    }
}