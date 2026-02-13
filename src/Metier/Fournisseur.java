/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author Héloïse
 */
public class Fournisseur {
    int id;
    String nom, prenom, telephone, adresse, dateCreate, UpdatedAt;

    public Fournisseur() {
    }

    
    public Fournisseur(int id, String nom, String prenom, String telephone, String adresse, String dateCreate) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateCreate = dateCreate;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }
    

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUpdatedAt(String UpdatedAt) {
        this.UpdatedAt = UpdatedAt;
    }
    
    
}
