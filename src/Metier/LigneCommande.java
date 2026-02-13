/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author YACOUBOU
 */

import java.time.LocalDateTime;

public class LigneCommande {
    private int idCommande;
    private int idArticle;
    private int quantite;
    private float prixUnitaire;
    private String date;

    public LigneCommande() {
    }

    public LigneCommande(int idCommande, int idArticle, int quantite, float prixUnitaire, String date) {
        this.idCommande = idCommande;
        this.idArticle = idArticle;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.date = date;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    
    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public String getDate() {
        return date;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    
}