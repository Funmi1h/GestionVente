/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author YACOUBOU
 */
// com.gestionvente.entity.Approvisionnement.java

import java.time.LocalDateTime;

public class Approvisionnement {

    private int idArticle;
    private int idFournisseur;
    private int quantite;
    private String dateApprovisionnement;
    private String updatedAt;

    // Relations optionnelles


    public Approvisionnement() {
    }

    public Approvisionnement(int idArticle, int idFournisseur, int quantite, String dateApprovisionnement, String updatedAt) {
        this.idArticle = idArticle;
        this.idFournisseur = idFournisseur;
        this.quantite = quantite;
        this.dateApprovisionnement = dateApprovisionnement;
        this.updatedAt = updatedAt;
    }

    
    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDateApprovisionnement() {
        return dateApprovisionnement;
    }

    public void setDateApprovisionnement(String dateApprovisionnement) {
        this.dateApprovisionnement = dateApprovisionnement;
    }
}