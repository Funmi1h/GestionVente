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
import java.sql.Connection;
import java.util.List;

public class CommandeModel {
    private CommandeDAO commandeDAO;
    private LigneCommandeDAO ligneCommandeDAO;
    private ClientDAO clientDAO;
    private ArticleDAO articleDAO;
    private PaiementDAO paiementDAO;
    
    public CommandeModel(Connection connection) {
        this.commandeDAO = new CommandeDAO(connection);
        this.ligneCommandeDAO = new LigneCommandeDAO(connection);
        this.clientDAO = new ClientDAO(connection);
        this.articleDAO = new ArticleDAO(connection);
        this.paiementDAO = new PaiementDAO(connection);
    }
    
    /**
     * Valider le panier (transformer en commande)
     */
    public boolean validerPanier(int idClient, String modePaiement) {
        try {
            // 1. Récupérer le panier
            Commande panier = commandeDAO.getPanierByClient(idClient);
            if (panier == null) {
                throw new Exception("Aucun panier trouvé pour ce client");
            }
            
            // 2. Vérifier que le panier n'est pas vide
            List<LigneCommande> lignes = ligneCommandeDAO.getLignesByCommande(panier.getIdCommande());
            if (lignes.isEmpty()) {
                throw new Exception("Le panier est vide");
            }
            
            // 3. Vérifier le stock pour tous les articles
            for (LigneCommande ligne : lignes) {
                Article article = articleDAO.rechercheArticle(ligne.getIdArticle());
                if (article == null) {
                    throw new Exception("Article introuvable: ID " + ligne.getIdArticle());
                }
                if (article.getStock() < ligne.getQuantite()) {
                    throw new Exception("Stock insuffisant pour: " + article.getNom() + 
                                      " (demandé: " + ligne.getQuantite() + ", disponible: " + article.getStock() + ")");
                }
            }
            
            // 4. Mettre à jour les stocks
            for (LigneCommande ligne : lignes) {
                if (!articleDAO.diminuerStock(ligne.getIdArticle(), ligne.getQuantite())) {
                    throw new Exception("Erreur lors de la mise à jour du stock pour l'article: " + ligne.getIdArticle());
                }
            }
            
            // 5. Changer le statut de la commande
            if (!commandeDAO.validerPanier(panier.getIdCommande())) {
                throw new Exception("Erreur lors de la validation de la commande");
            }
            
            // 6. Calculer le total de la commande
            double total = calculerTotalCommande(panier.getIdCommande());
            
            // 7. Enregistrer le paiement
            Paiement paiement = new Paiement();
            paiement.setIdCommande(panier.getIdCommande());
            paiement.setMontant(total);
            paiement.setModePaiement(modePaiement);
            
            if (!paiementDAO.ajouter(paiement)) {
                throw new Exception("Erreur lors de l'enregistrement du paiement");
            }
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Erreur validation panier: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Calculer le total d'une commande
     */
    public double calculerTotalCommande(int idCommande) {
        List<LigneCommande> lignes = ligneCommandeDAO.getLignesByCommande(idCommande);
        double total = 0.0;
        
        for (LigneCommande ligne : lignes) {
            Article article = articleDAO.rechercheArticle(ligne.getIdArticle());
            if (article != null) {
                total += article.getPrix() * ligne.getQuantite();
            }
        }
        
        return total;
    }
    
    /**
     * Récupérer les commandes d'un client
     */
    public List<Commande> getCommandesParClient(int idClient) {
        if (idClient <= 0) {
            throw new IllegalArgumentException("ID client invalide");
        }
        
        return commandeDAO.getCommandesByClient(idClient);
    }
    
    /**
     * Récupérer toutes les commandes (pour admin)
     */
    public List<Commande> listerToutesCommandes() {
        return commandeDAO.listerToutes();
    }
    
    /**
     * Récupérer les commandes par statut
     */
    public List<Commande> getCommandesParStatut(String statut) {
        if (statut == null || statut.trim().isEmpty()) {
            throw new IllegalArgumentException("Statut invalide");
        }
        
        List<Commande> toutesCommandes = commandeDAO.listerToutes();
        return toutesCommandes.stream()
                .filter(c -> statut.equals(c.getStatut()))
                .toList();
    }
    
    /**
     * Récupérer les détails d'une commande
     */
    public CommandeDetails getDetailsCommande(int idCommande) {
        Commande commande = commandeDAO.getPanierByClient(idCommande);
        if (commande == null) {
            return null;
        }
        
        List<LigneCommande> lignes = ligneCommandeDAO.getLignesByCommande(idCommande);
        Client client = clientDAO.rechercheClient(commande.getIdClient());
        Paiement paiement = paiementDAO.findByCommandeId(idCommande);
        
        return new CommandeDetails(commande, client, lignes, paiement);
    }
    
    /**
     * Changer le statut d'une commande
     */
    public boolean changerStatutCommande(int idCommande, String nouveauStatut) {
        if (idCommande <= 0 || nouveauStatut == null || nouveauStatut.trim().isEmpty()) {
            return false;
        }
        
        return commandeDAO.changerStatut(idCommande, nouveauStatut);
    }
    
    /**
     * Annuler une commande (et restocker les articles)
     */
    public boolean annulerCommande(int idCommande) {
        try {
            // Récupérer les lignes de commande
            List<LigneCommande> lignes = ligneCommandeDAO.getLignesByCommande(idCommande);
            
            // Restocker les articles
            for (LigneCommande ligne : lignes) {
                articleDAO.augmenterStock(ligne.getIdArticle(), ligne.getQuantite());
            }
            
            // Changer le statut à ANNULEE
            return commandeDAO.changerStatut(idCommande, "ANNULEE");
            
        } catch (Exception e) {
            System.err.println("Erreur annulation commande: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtenir le chiffre d'affaires total
     */
    public double getChiffreAffairesTotal() {
        List<Commande> commandesPayees = getCommandesParStatut("PAYEE");
        double total = 0.0;
        
        for (Commande commande : commandesPayees) {
            total += calculerTotalCommande(commande.getIdCommande());
        }
        
        return total;
    }
    
    /**
     * Classe interne pour les détails de commande
     */
    public class CommandeDetails {
        private Commande commande;
        private Client client;
        private List<LigneCommande> lignes;
        private Paiement paiement;
        
        public CommandeDetails(Commande commande, Client client, List<LigneCommande> lignes, Paiement paiement) {
            this.commande = commande;
            this.client = client;
            this.lignes = lignes;
            this.paiement = paiement;
        }
        
        // Getters
        public Commande getCommande() { return commande; }
        public Client getClient() { return client; }
        public List<LigneCommande> getLignes() { return lignes; }
        public Paiement getPaiement() { return paiement; }
    }
}