/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles;

/**
 *
 * @author YACOUBOU
 */
import Metier.*;
import DAO.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class PanierModel {
    private CommandeDAO commandeDAO;
    private LigneCommandeDAO ligneCommandeDAO;
    private ArticleDAO articleDAO;
    private ClientDAO clientDAO;
    
    public PanierModel(Connection connection) {
        this.commandeDAO = new CommandeDAO(connection);
        this.ligneCommandeDAO = new LigneCommandeDAO(connection);
        this.articleDAO = new ArticleDAO(connection);
        this.clientDAO = new ClientDAO(connection);
    }
    
    /**
     * Créer ou récupérer le panier d'un client
     */
    public Commande getOuCreerPanier(int idClient) {
        // Vérifier que le client existe
        Client client = clientDAO.rechercheClient(idClient);
        if (client == null) {
            throw new IllegalArgumentException("Client introuvable");
        }
        
        // Chercher un panier existant
        Commande panier = commandeDAO.getPanierByClient(idClient);
        
        // Si pas de panier, en créer un
        if (panier == null) {
            commandeDAO.creerPanier(idClient);
            panier = commandeDAO.getPanierByClient(idClient);
        }
        
        return panier;
    }
    
    /**
     * Ajouter un article au panier
     */
    public boolean ajouterArticleAuPanier(int idClient, int idArticle, int quantite) {
        try {
            // 1. Vérifier le client
            Client client = clientDAO.rechercheClient(idClient);
            if (client == null) {
                throw new Exception("Client introuvable");
            }
            
            // 2. Vérifier l'article et le stock
            Article article = articleDAO.rechercheArticle(idArticle);
            if (article == null) {
                throw new Exception("Article introuvable");
            }
            if (!article.estDisponible(quantite)) {
                throw new Exception("Stock insuffisant pour l'article: " + article.getNom());
            }
            
            // 3. Récupérer ou créer le panier
            Commande panier = getOuCreerPanier(idClient);
            
            // 4. Vérifier si l'article est déjà dans le panier
            List<LigneCommande> lignes = ligneCommandeDAO.getLignesByCommande(panier.getIdCommande());
            LigneCommande ligneExistante = lignes.stream()
                    .filter(l -> l.getIdArticle() == idArticle)
                    .findFirst()
                    .orElse(null);
            
            int quantiteTotale = quantite;
            if (ligneExistante != null) {
                quantiteTotale += ligneExistante.getQuantite();
                
                // Vérifier le stock pour la quantité totale
                if (!article.estDisponible(quantiteTotale)) {
                    throw new Exception("Quantité totale (" + quantiteTotale + ") dépasse le stock disponible (" + article.getStock() + ")");
                }
            }
            
            // 5. Ajouter ou mettre à jour la ligne de commande
            LigneCommande ligne = new LigneCommande();
            ligne.setIdCommande(panier.getIdCommande());
            ligne.setIdArticle(idArticle);
            ligne.setQuantite(quantite);
            ligne.setPrixUnitaire(article.getPrix());
            
            return ligneCommandeDAO.ajouterAuPanier(ligne);
            
        } catch (Exception e) {
            System.err.println("Erreur ajout au panier: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Récupérer le contenu du panier
     */
    public List<LigneCommande> getContenuPanier(int idClient) {
        Commande panier = commandeDAO.getPanierByClient(idClient);
        if (panier != null) {
            return ligneCommandeDAO.getLignesByCommande(panier.getIdCommande());
        }
        return List.of();
    }
    
    /**
     * Calculer le total du panier
     */
    public float calculerTotalPanier(int idClient) {
        List<LigneCommande> lignes = getContenuPanier(idClient);
        float total = 0;
        
        for (LigneCommande ligne : lignes) {
            Article article = articleDAO.rechercheArticle(ligne.getIdArticle());
            if (article != null) {
                float ligneTotal = article.getPrix()*ligne.getQuantite();
                total = total+ligneTotal;
            }
        }
        
        return total;
    }
    
    /**
     * Modifier la quantité d'un article dans le panier
     */
    public boolean modifierQuantitePanier(int idClient, int idArticle, int nouvelleQuantite) {
        if (nouvelleQuantite <= 0) {
            // Si quantité <= 0, supprimer l'article
            return supprimerArticleDuPanier(idClient, idArticle);
        }
        
        try {
            // Vérifier le stock
            Article article = articleDAO.rechercheArticle(idArticle);
            if (article == null || !article.estDisponible(nouvelleQuantite)) {
                throw new Exception("Stock insuffisant");
            }
            
            // Récupérer le panier
            Commande panier = commandeDAO.getPanierByClient(idClient);
            if (panier == null) {
                throw new Exception("Panier introuvable");
            }
            
            // Mettre à jour la quantité
            return ligneCommandeDAO.modifierQuantite(panier.getIdCommande(), idArticle, nouvelleQuantite);
            
        } catch (Exception e) {
            System.err.println("Erreur modification quantité: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Supprimer un article du panier
     */
    public boolean supprimerArticleDuPanier(int idClient, int idArticle) {
        Commande panier = commandeDAO.getPanierByClient(idClient);
        if (panier != null) {
            return ligneCommandeDAO.supprimer(panier.getIdCommande(), idArticle);
        }
        return false;
    }
    
    /**
     * Vider complètement le panier
     */
    public boolean viderPanier(int idClient) {
        Commande panier = commandeDAO.getPanierByClient(idClient);
        if (panier != null) {
            return ligneCommandeDAO.supprimerTout(panier.getIdCommande());
        }
        return false;
    }
    
    /**
     * Obtenir le nombre d'articles dans le panier
     */
    public int getNombreArticlesDansPanier(int idClient) {
        List<LigneCommande> lignes = getContenuPanier(idClient);
        return lignes.stream()
                .mapToInt(LigneCommande::getQuantite)
                .sum();
    }
    
    /**
     * Vérifier si le panier est vide
     */
    public boolean panierEstVide(int idClient) {
        return getContenuPanier(idClient).isEmpty();
    }
}