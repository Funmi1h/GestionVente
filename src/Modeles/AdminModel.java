/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles;

/**
 *
 * @author YACOUBOU
 */
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class AdminModel {
    private ClientModel clientModel;
    private ArticleModel articleModel;
    private CommandeModel commandeModel;
    private PaiementModel paiementModel;
    
    public AdminModel(Connection connection) {
        this.clientModel = new ClientModel(connection);
        this.articleModel = new ArticleModel(connection);
        this.commandeModel = new CommandeModel(connection);
        this.paiementModel = new PaiementModel(connection);
    }
    
    /**
     * Obtenir les statistiques globales
     */
    public Map<String, Object> getStatistiquesGlobales() {
        Map<String, Object> stats = new java.util.HashMap<>();
        
        // Statistiques clients
        stats.put("nombreClients", clientModel.getNombreTotalClients());
        
        // Statistiques articles
        stats.put("nombreArticles", articleModel.getNombreTotalArticles());
        stats.put("valeurStock", articleModel.getValeurTotaleStock());
        
        // Statistiques commandes
        stats.put("chiffreAffaires", commandeModel.getChiffreAffairesTotal());
        
        // Statistiques paiements
        stats.put("totalPaiements", paiementModel.getTotalPaiements());
        stats.put("statsModePaiement", paiementModel.getStatistiquesParMode());
        
        return stats;
    }
    
    /**
     * Obtenir le rapport des ventes : Facultatif
     */
    public List<Map<String, Object>> getRapportVentes(String periode) {
        // Implémentation à faire après avoir vue l'interface, ...
        /*
        Données statique:
            Map.of("mois", "Janvier", "ventes", 15000.0, "commandes", 45),
            Map.of("mois", "Février", "ventes", 18000.0, "commandes", 52),
            Map.of("mois", "Mars", "ventes", 22000.0, "commandes", 61)
        */
        return List.of(
                Map.of("Mois", "Jour")
        );
    }
    
    /**
     * Obtenir les articles les plus vendus
     */
    public List<Map<String, Object>> getTopArticles() {
        // Implémentation simplifiée
        return List.of(
            Map.of("article", "Ordinateur Portable", "ventes", 15, "revenu", 13499.85),
            Map.of("article", "Souris Sans Fil", "ventes", 42, "revenu", 1259.58),
            Map.of("article", "Clavier Mécanique", "ventes", 18, "revenu", 1619.82)
        );
    }
    
    /**
     * Obtenir les clients les plus actifs
     */
    public List<Map<String, Object>> getTopClients() {
        // Implémentation simplifiée
        return List.of(
            Map.of("client", "Jean Dupont", "commandes", 8, "montant", 2450.0),
            Map.of("client", "Marie Martin", "commandes", 5, "montant", 1899.95),
            Map.of("client", "Pierre Durand", "commandes", 4, "montant", 1125.50)
        );
    }
    
    /**
     * Sauvegarder les données (backup)
     */
    public boolean sauvegarderDonnees() {
        // A implémenté
        
        System.out.println("Sauvegarde des données en cours...");
        return true;
    }
    
    /**
     * Restaurer les données
     */
    public boolean restaurerDonnees(String fichierBackup) {
        // Logique de restauration
        System.out.println("Restauration depuis: " + fichierBackup);
        return true;
    }
}