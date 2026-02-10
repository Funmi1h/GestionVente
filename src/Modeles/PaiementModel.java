/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles;

/**
 *
 * @author YACOUBOU
 */

import Metier.Paiement;
import DAO.PaiementDAO;
import DAO.CommandeDAO;
import java.sql.Connection;
import java.util.List;

public class PaiementModel {
    private PaiementDAO paiementDAO;
    private CommandeDAO commandeDAO;
    
    public PaiementModel(Connection connection) {
        this.paiementDAO = new PaiementDAO(connection);
        this.commandeDAO = new CommandeDAO(connection);
    }
    
    /**
     * Enregistrer un paiement
     */
    public boolean enregistrerPaiement(Paiement paiement) {
        // Validation des données
        if (paiement.getIdCommande() <= 0) {
            throw new IllegalArgumentException("ID commande invalide");
        }
        if (paiement.getMontant() <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        if (paiement.getModePaiement() == null || paiement.getModePaiement().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mode de paiement est obligatoire");
        }
        
        // Vérifier que la commande existe
        if (commandeDAO.getPanierByClient(paiement.getIdCommande()) == null) {
            throw new IllegalArgumentException("Commande introuvable");
        }
        
        // Vérifier qu'il n'y a pas déjà un paiement pour cette commande
        if (paiementDAO.findByCommandeId(paiement.getIdCommande()) != null) {
            throw new IllegalArgumentException("Un paiement existe déjà pour cette commande");
        }
        
        return paiementDAO.ajouter(paiement);
    }
    
    /**
     * Récupérer un paiement par ID
     */
    public Paiement trouverPaiementParId(int idPaiement) {
        if (idPaiement <= 0) {
            throw new IllegalArgumentException("ID paiement invalide");
        }
        
        return paiementDAO.findById(idPaiement);
    }
    
    /**
     * Récupérer le paiement d'une commande
     */
    public Paiement trouverPaiementParCommande(int idCommande) {
        if (idCommande <= 0) {
            throw new IllegalArgumentException("ID commande invalide");
        }
        
        return paiementDAO.findByCommandeId(idCommande);
    }
    
    /**
     * Récupérer tous les paiements
     */
    public List<Paiement> listerTousPaiements() {
        return paiementDAO.listerTous();
    }
    
    /**
     * Récupérer les paiements par mode
     */
    public List<Paiement> listerPaiementsParMode(String modePaiement) {
        if (modePaiement == null || modePaiement.trim().isEmpty()) {
            throw new IllegalArgumentException("Mode de paiement invalide");
        }
        
        List<Paiement> tousPaiements = paiementDAO.listerTous();
        return tousPaiements.stream()
                .filter(p -> modePaiement.equalsIgnoreCase(p.getModePaiement()))
                .toList();
    }
    
    /**
     * Récupérer les paiements par période
     */
    public List<Paiement> listerPaiementsParPeriode(String dateDebut, String dateFin) {
        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Dates invalides");
        }
        
        List<Paiement> tousPaiements = paiementDAO.listerTous();
        return tousPaiements.stream()
                .filter(p -> p.getDatePaiement().compareTo(dateDebut) >= 0 && 
                            p.getDatePaiement().compareTo(dateFin) <= 0)
                .toList();
    }
    
    /**
     * Obtenir le montant total des paiements
     */
    public double getTotalPaiements() {
        List<Paiement> paiements = paiementDAO.listerTous();
        return paiements.stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }
    
    /**
     * Obtenir les statistiques par mode de paiement
     */
    public java.util.Map<String, Double> getStatistiquesParMode() {
        List<Paiement> paiements = paiementDAO.listerTous();
        java.util.Map<String, Double> stats = new java.util.HashMap<>();
        
        for (Paiement paiement : paiements) {
            String mode = paiement.getModePaiement();
            double montant = paiement.getMontant();
            
            stats.put(mode, stats.getOrDefault(mode, 0.0) + montant);
        }
        
        return stats;
    }
    
    /**
     * Générer une facture
     */
    public String genererFacture(int idPaiement) {
        Paiement paiement = paiementDAO.findById(idPaiement);
        if (paiement == null) {
            return "Paiement introuvable";
        }
        
        // Récupérer les détails de la commande 
        
        StringBuilder facture = new StringBuilder();
        facture.append("=== FACTURE ===\n");
        facture.append("Numéro paiement: ").append(paiement.getIdPaiement()).append("\n");
        facture.append("Date: ").append(paiement.getDatePaiement()).append("\n");
        facture.append("Montant: ").append(String.format("%.2f", paiement.getMontant())).append(" FCFA\n");
        facture.append("Mode: ").append(paiement.getModePaiement()).append("\n");
        facture.append("================\n");
        
        return facture.toString();
    }
}