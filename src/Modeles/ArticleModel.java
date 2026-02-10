/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles;

import Metier.Article;
import DAO.ArticleDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
/**
 *
 * @author YACOUBOU
 */


public class ArticleModel {
    private ArticleDAO articleDAO;
    
    public ArticleModel(Connection connection) {
        this.articleDAO = new ArticleDAO(connection);
    }
    
    /**
     * Ajouter un nouvel article
     */
    public boolean ajouterArticle(Article article) {
        // Validation des données
        if (article.getNom() == null || article.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'article est obligatoire");
        }
        if (article.getPrix() <= 0) {
            throw new IllegalArgumentException("Le prix doit être supérieur à 0");
        }
        if (article.getStock() < 0) {
            throw new IllegalArgumentException("Le stock ne peut pas être négatif");
        }
        
        return articleDAO.ajouter(article);
    }
    
    /**
     * Modifier un article existant
     */
    public boolean modifierArticle(Article article) {
        if (article.getId_article() <= 0) {
            throw new IllegalArgumentException("ID article invalide");
        }
        
        // Validation des données
        if (article.getNom() == null || article.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'article est obligatoire");
        }
        
        return articleDAO.modifier(article, article.getId_article());
    }
    
    /**
     * Supprimer un article
     */
    public boolean supprimerArticle(int idArticle) {
        if (idArticle <= 0) {
            throw new IllegalArgumentException("ID article invalide");
        }
        
        // Vérifier si l'article est dans des commandes avant suppression
        // Cette logique peut être ajoutée ici
        
        return articleDAO.supprime(idArticle);
    }
    
    /**
     * Récupérer tous les articles
     */
    public List<Article> listerTousArticles() {
        return articleDAO.lists();
    }
    
    /**
     * Rechercher un article par ID
     */
    public Article trouverArticleParId(int idArticle) {
        if (idArticle <= 0) {
            throw new IllegalArgumentException("ID article invalide");
        }
        
        return articleDAO.rechercheArticle(idArticle);
    }
    
    /**
     * Rechercher des articles par nom
     */
    public List<Article> rechercherArticlesParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de recherche est obligatoire");
        }
        
        List<Article> tousArticles = articleDAO.lists();
        return tousArticles.stream()
                .filter(a -> a.getNom().toLowerCase().contains(nom.toLowerCase()))
                .toList();
    }
    
    /**
     * Filtrer les articles par prix (min/max)
     */
    public List<Article> filtrerArticlesParPrix(float prixMin, float prixMax) {
        if (prixMin < 0 || prixMax < prixMin) {
            throw new IllegalArgumentException("Intervalle de prix invalide");
        }
        
        List<Article> tousArticles = articleDAO.lists();
        return tousArticles.stream()
                .filter(a -> a.getPrix() >= prixMin && a.getPrix() <= prixMax)
                .toList();
    }
    
    /**
     * Vérifier si un article est en stock
     */
    public boolean verifierStockDisponible(int idArticle, int quantite) {
        if (idArticle <= 0 || quantite <= 0) {
            return false;
        }
        
        Article article = articleDAO.rechercheArticle(idArticle);
        return article != null && article.getStock() >= quantite;
    }
    
    /**
     * Mettre à jour le stock d'un article
     */
    public boolean mettreAJourStock(int idArticle, int nouvelleQuantite) {
        if (idArticle <= 0 || nouvelleQuantite < 0) {
            return false;
        }
        
        return articleDAO.mettreAJourStock(idArticle, nouvelleQuantite);
    }
    
    /**
     * Diminuer le stock (après vente)
     */
    public boolean diminuerStock(int idArticle, int quantite) {
        if (idArticle <= 0 || quantite <= 0) {
            return false;
        }
        
        Article article = articleDAO.rechercheArticle(idArticle);
        if (article == null || article.getStock() < quantite) {
            return false;
        }
        
        int nouveauStock = article.getStock() - quantite;
        return articleDAO.mettreAJourStock(idArticle, nouveauStock);
    }
    
    /**
     * Augmenter le stock (après réapprovisionnement)
     */
    public boolean augmenterStock(int idArticle, int quantite) {
        if (idArticle <= 0 || quantite <= 0) {
            return false;
        }
        
        Article article = articleDAO.rechercheArticle(idArticle);
        if (article == null) {
            return false;
        }
        
        int nouveauStock = article.getStock() + quantite;
        return articleDAO.mettreAJourStock(idArticle, nouveauStock);
    }
    
        public boolean dimunuerStock(int idArticle, int quantite) {
        if (idArticle <= 0 || quantite <= 0) {
            return false;
        }
        
        Article article = articleDAO.rechercheArticle(idArticle);
        if (article == null) {
            return false;
        }
        
        int nouveauStock = article.getStock() - quantite;
        return articleDAO.mettreAJourStock(idArticle, nouveauStock);
    }
    
    /**
     * Obtenir le nombre total d'articles
     */
    public int getNombreTotalArticles() {
        return articleDAO.lists().size();
    }
    
    /**
     * Obtenir la valeur totale du stock
     */
    public float getValeurTotaleStock() {
        List<Article> articles = articleDAO.lists();
        return articles.stream()
                .map(a -> a.getPrix()*a.getStock())
                .reduce(0f, Float::sum);
    }
}