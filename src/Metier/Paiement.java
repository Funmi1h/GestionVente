/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author YACOUBOU
 */
public class Paiement {
    private int idPaiement;
    private String datePaiement;
    private double montant;
    private String modePaiement;
    private int idCommande;

    public Paiement() {
    }

    public Paiement(int idPaiement, String datePaiement, double montant, String modePaiement, int idCommande) {
        this.idPaiement = idPaiement;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.idCommande = idCommande;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public int getIdPaiement() {
        return idPaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public double getMontant() {
        return montant;
    }

    public String getDatePaiement() {
        return datePaiement;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setIdPaiement(int idPaiement) {
        this.idPaiement = idPaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDatePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
    }
    
    
}
