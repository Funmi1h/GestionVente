/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author YACOUBOU
 */
public class Commande {
    public static final String STATUT_EN_COURS = "EN_COURS";
    public static final String STATUT_PAYEE = "PAYEE";
    public static final String STATUT_LIVREE = "LIVREE";
    public static final String STATUT_ANNULEE = "ANNULEE";
    
    private int idCommande;
    private String dateCommande;
    private String statut;
    private int idClient;

    public Commande(String statut, int idClient) {
        this.statut = statut;
        this.idClient = idClient;
    }

    public Commande() {
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public String getStatut() {
        return statut;
    }

    public static String getSTATUT_ANNULEE() {
        return STATUT_ANNULEE;
    }

    public static String getSTATUT_EN_COURS() {
        return STATUT_EN_COURS;
    }

    public static String getSTATUT_LIVREE() {
        return STATUT_LIVREE;
    }

    public static String getSTATUT_PAYEE() {
        return STATUT_PAYEE;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    
}
